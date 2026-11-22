package com.greenapi.client.pkg.api.calls;

import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

final class AudioFiles {
    private static final int WAV_HEADER = 12;
    private static final int CHUNK_HEADER = 8;
    private static final int PCM_FORMAT = 1;
    private static final int EXTENSIBLE_FORMAT = 0xFFFE;

    private AudioFiles() {
    }

    static byte[] readAll(InputStream input) {
        try (input) {
            var output = new ByteArrayOutputStream();
            var buffer = new byte[64 * 1024];
            int read;
            while ((read = input.read(buffer)) >= 0) {
                output.write(buffer, 0, read);
                if (output.size() > MAX_SOURCE_BYTES) {
                    throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_TOO_LARGE);
                }
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new CallException(CallErrorCode.INVALID_AUDIO, CallErrorCode.INVALID_AUDIO.getMessage() + ": " + e.getMessage(), null, null, e);
        }
    }

    static byte[] toCallPcm(byte[] data, String ffmpegPath) {
        if (isWav(data)) {
            var pcm = readWav(data);
            if (pcm != null) {
                return pcm;
            }
        }
        return ffmpeg(data, ffmpegPath);
    }

    private static boolean isWav(byte[] data) {
        return data.length > WAV_HEADER
            && "RIFF".equals(new String(data, 0, 4, StandardCharsets.US_ASCII))
            && "WAVE".equals(new String(data, 8, 4, StandardCharsets.US_ASCII));
    }

    private static byte[] readWav(byte[] data) {
        var buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        int offset = WAV_HEADER;
        int channels = 0;
        int sampleRate = 0;
        int bits = 0;
        int format = 0;
        while (offset + CHUNK_HEADER <= data.length) {
            var id = new String(data, offset, 4, StandardCharsets.US_ASCII);
            var size = buffer.getInt(offset + 4);
            var body = offset + CHUNK_HEADER;
            if ("fmt ".equals(id)) {
                format = Short.toUnsignedInt(buffer.getShort(body));
                channels = buffer.getShort(body + 2);
                sampleRate = buffer.getInt(body + 4);
                bits = buffer.getShort(body + 14);
            } else if ("data".equals(id)) {
                if ((format != PCM_FORMAT && format != EXTENSIBLE_FORMAT) || bits != AUDIO_BITS || channels < 1) {
                    return null;
                }
                var length = Math.min(size, data.length - body);
                var samples = new short[length / 2];
                ByteBuffer.wrap(data, body, samples.length * 2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
                var mono = Resampler.toMono(samples, channels);
                return Resampler.toBytes(new Resampler(sampleRate, AUDIO_SAMPLE_RATE).process(mono));
            }
            offset = body + size + (size % 2);
        }
        throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_BAD_WAV);
    }

    private static byte[] ffmpeg(byte[] data, String ffmpegPath) {
        var command = new ArrayList<String>();
        command.add(ffmpegPath);
        command.addAll(FFMPEG_ARGS);
        Process process;
        try {
            process = new ProcessBuilder(command).start();
        } catch (IOException e) {
            throw new CallException(CallErrorCode.FFMPEG_NOT_FOUND, CallErrorCode.FFMPEG_NOT_FOUND.getMessage(), null, null, e);
        }
        var writer = CompletableFuture.runAsync(() -> {
            try (var stdin = process.getOutputStream()) {
                stdin.write(data);
            } catch (IOException ignored) {
            }
        });
        var errors = CompletableFuture.supplyAsync(() -> {
            try (var stderr = process.getErrorStream()) {
                return new String(stderr.readAllBytes(), StandardCharsets.UTF_8).trim();
            } catch (IOException e) {
                return "";
            }
        });
        try (var stdout = process.getInputStream()) {
            var pcm = stdout.readAllBytes();
            var exit = process.waitFor();
            writer.join();
            if (exit != 0) {
                var detail = errors.join();
                throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_FFMPEG + ": " + (detail.isEmpty() ? "exit code " + exit : detail));
            }
            return pcm;
        } catch (IOException e) {
            throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_FFMPEG + ": " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            process.destroy();
            throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_STOPPED);
        }
    }
}
