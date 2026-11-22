package com.greenapi.client.pkg.api.calls;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.api.methods.GreenApiCalls;
import com.greenapi.client.pkg.models.calls.CallEnd;
import com.greenapi.client.pkg.models.calls.CallState;
import com.greenapi.client.pkg.models.calls.CallStateKind;
import com.greenapi.client.pkg.models.calls.CallsConnectionOptions;
import com.greenapi.client.pkg.models.calls.CallsDisconnect;
import com.greenapi.client.pkg.models.response.IceServer;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

/**
 * Live connection to the calls of an instance (the callsRtc WebSocket) and the audio of the current call.
 * Create it with greenApi.calls.connectCalls() and control the call with the greenApi.calls methods.
 * The audio needs the optional dependency dev.onvoid.webrtc:webrtc-java.
 *
 * <pre>{@code
 * try (var calls = greenApi.calls.connectCalls()) {
 *     calls.open();
 *     greenApi.calls.callsDial(new CallsDialReq("79001234567@c.us"));
 *     calls.startAudioBridge();
 *     calls.waitFor(CallStateKind.ON_CALL, Duration.ofSeconds(60));
 *     calls.play(Path.of("hello.wav"));
 *     greenApi.calls.callsHangUp();
 * }
 * }</pre>
 */
@Log4j2
public class CallsConnection implements AutoCloseable {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final GreenApiCalls api;
    private final CallsConnectionOptions options;
    private final ExecutorService events = Executors.newSingleThreadExecutor(daemon("green-api-calls-events"));
    private final ExecutorService callbacks = Executors.newSingleThreadExecutor(daemon("green-api-calls-callbacks"));
    private final ScheduledExecutorService timers = Executors.newSingleThreadScheduledExecutor(daemon("green-api-calls-timers"));
    private final List<CallsListener> listeners = new CopyOnWriteArrayList<>();
    private final List<Waiter> waiters = new ArrayList<>();
    private final CallsSocket socket;
    private final Resampler sendResampler;
    private volatile CallState state;
    private volatile AudioBridge bridge;
    private volatile Playback playback;
    private CompletableFuture<Void> pendingBridge;
    private CompletableFuture<CallState> firstState;
    private AudioBridge.Engine engine;
    private Resampler remoteResampler;
    private int remoteRate;
    private boolean resumePending;
    private boolean refusalReported;
    private volatile boolean closed;

    private record Waiter(CallStateKind kind, CompletableFuture<CallState> future) {
    }

    private static final class Playback {
        private final CompletableFuture<Void> stopped = new CompletableFuture<>();
    }

    /**
     * Creates the connection. Use greenApi.calls.connectCalls() instead.
     */
    public CallsConnection(GreenApiCalls api, String url, CallsConnectionOptions options) {
        if (!PCM_SAMPLE_RATES.contains(options.getPcmSampleRate())) {
            throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_SAMPLE_RATE);
        }
        this.api = api;
        this.options = options;
        this.sendResampler = new Resampler(options.getPcmSampleRate(), AUDIO_SAMPLE_RATE);
        this.socket = new CallsSocket(url, events, timers, this::onMessage, () -> emit(CallsListener::onConnect), this::onSocketDisconnect);
    }

    public void addListener(CallsListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CallsListener listener) {
        listeners.remove(listener);
    }

    /**
     * The latest call state of the instance, null before the first state arrives or after the connection is lost.
     */
    public CallState getState() {
        return state;
    }

    /**
     * Whether the audio of a call is connected or connecting now.
     */
    public boolean hasAudioBridge() {
        return bridge != null;
    }

    /**
     * Opens the connection and waits for the current call state. Reconnects on its own after network drops until close().
     *
     * @throws CallException INVALID_CREDENTIALS, NOT_IN_TARIFF, CALLS_NOT_ENABLED or CONNECTION_FAILED
     */
    public CallState open() {
        var first = call(() -> {
            ensureOpen();
            if (socket.isOpen() && state != null) {
                return CompletableFuture.completedFuture(state);
            }
            firstState = new CompletableFuture<>();
            var waiting = firstState;
            socket.open().whenComplete((ignored, error) -> {
                if (error != null) {
                    waiting.completeExceptionally(error);
                }
            });
            return waiting;
        });
        return await(first, OPEN_TIMEOUT, CallErrorCode.CONNECTION_FAILED, "The calls server did not send the call state in time");
    }

    /**
     * Connects the audio of the current call. Call it after callsDial or callsAccept has returned.
     *
     * @throws CallException if there is no call, the call ends first, or the audio cannot start
     */
    public void startAudioBridge() {
        var pending = call(() -> {
            ensureOpen();
            if (bridge != null || pendingBridge != null) {
                throw new CallException(CallErrorCode.AUDIO_BUSY);
            }
            pendingBridge = new CompletableFuture<>();
            return pendingBridge;
        });
        List<IceServer> iceServers;
        try {
            iceServers = api.callsGetIceServers().getBody();
        } catch (RuntimeException e) {
            run(() -> {
                if (pendingBridge == pending) {
                    pendingBridge = null;
                }
            });
            throw CallErrors.wrap(e);
        }
        var servers = iceServers == null ? List.<IceServer>of() : iceServers;
        run(() -> createBridge(pending, servers));
        try {
            await(pending, ANSWER_TIMEOUT, CallErrorCode.AUDIO_FAILED, CallErrorCode.AUDIO_FAILED.getMessage() + ": " + DETAIL_NO_ANSWER);
        } catch (CallException e) {
            run(() -> {
                if (pendingBridge == pending) {
                    pendingBridge = null;
                    teardownBridge(false);
                }
            });
            throw e;
        }
    }

    /**
     * Disconnects the audio of the call. The call itself goes on, end it with callsHangUp.
     */
    public void stopAudioBridge() {
        run(() -> {
            resumePending = false;
            rejectPendingBridge(CallErrorCode.AUDIO_FAILED, DETAIL_STOPPED);
            teardownBridge(true);
        });
    }

    /**
     * Plays an audio file into the call and returns when it has been played, stopped, or the call ended.
     * WAV files (16-bit PCM) play as they are, other formats go through ffmpeg. Waits until the audio is connected
     * and the other side is heard. A new play stops the previous one and drops queued live audio.
     *
     * @throws CallException AUDIO_NOT_STARTED, AUDIO_FAILED, INVALID_AUDIO or FFMPEG_NOT_FOUND
     */
    public void play(Path file) {
        byte[] data;
        try {
            if (Files.size(file) > MAX_SOURCE_BYTES) {
                throw CallErrors.audio(CallErrorCode.INVALID_AUDIO, DETAIL_TOO_LARGE);
            }
            data = Files.readAllBytes(file);
        } catch (java.io.IOException e) {
            throw new CallException(CallErrorCode.INVALID_AUDIO, CallErrorCode.INVALID_AUDIO.getMessage() + ": " + e.getMessage(), null, null, e);
        }
        playPcm(data);
    }

    /**
     * Plays audio from a stream into the call. The whole stream is read first. See {@link #play(Path)}.
     */
    public void play(InputStream audio) {
        playPcm(AudioFiles.readAll(audio));
    }

    /**
     * Stops the audio that is playing.
     */
    public void stopPlaying() {
        var current = playback;
        playback = null;
        if (current != null) {
            current.stopped.complete(null);
            var active = bridge;
            if (active != null) {
                active.pcm().clear();
            }
        }
    }

    /**
     * Sends live audio into the call: 16-bit little-endian mono PCM at pcmSampleRate (24000 by default).
     * Chunks of any size are queued and sent in real time. Stops a running play().
     *
     * @throws CallException AUDIO_NOT_STARTED when the audio is not started
     */
    public void sendPcm(byte[] pcm) {
        var active = bridge;
        if (active == null) {
            throw new CallException(CallErrorCode.AUDIO_NOT_STARTED);
        }
        stopPlaying();
        short[] converted;
        synchronized (sendResampler) {
            converted = sendResampler.process(Resampler.toSamples(pcm, pcm.length - pcm.length % 2));
        }
        active.pcm().write(Resampler.toBytes(converted));
    }

    /**
     * Drops the live audio that is queued and stops play(), for example when the other side starts talking.
     */
    public void clearAudio() {
        stopPlaying();
        var active = bridge;
        if (active != null) {
            active.pcm().clear();
        }
        synchronized (sendResampler) {
            sendResampler.reset();
        }
    }

    /**
     * How much live audio is queued and not yet sent, in milliseconds.
     */
    public int pendingAudioMs() {
        var active = bridge;
        return active == null ? 0 : active.pcm().pendingMs();
    }

    /**
     * Waits until the queued live audio has been sent, cleared, or the audio stopped.
     */
    public void drainAudio() {
        var active = bridge;
        if (active != null) {
            active.pcm().drained().join();
        }
    }

    /**
     * Waits until the call reaches a state.
     *
     * @throws CallException CALL_ENDED with the reason if the call ends first, TIMEOUT, or the connection error
     */
    public CallState waitFor(CallStateKind kind, Duration timeout) {
        var waiting = call(() -> {
            var current = state;
            if (current != null && current.getState() == kind) {
                return CompletableFuture.completedFuture(current);
            }
            var waiter = new Waiter(kind, new CompletableFuture<>());
            waiters.add(waiter);
            return waiter.future();
        });
        try {
            return await(waiting, timeout, CallErrorCode.TIMEOUT, "The call did not reach the " + kind.getValue() + " state in time");
        } finally {
            run(() -> waiters.removeIf(waiter -> waiter.future() == waiting));
        }
    }

    /**
     * Disconnects the audio and closes the connection. The call itself is not ended.
     */
    @Override
    public void close() {
        if (closed) {
            return;
        }
        run(() -> {
            closed = true;
            resumePending = false;
            rejectPendingBridge(CallErrorCode.AUDIO_FAILED, DETAIL_STOPPED);
            teardownBridge(false);
            if (firstState != null) {
                firstState.completeExceptionally(new CallException(CallErrorCode.CONNECTION_FAILED, "The calls connection was closed"));
                firstState = null;
            }
            failWaiters(new CallException(CallErrorCode.CONNECTION_FAILED, "The calls connection was closed"));
            socket.close();
            if (engine != null) {
                engine.dispose();
                engine = null;
            }
        });
        timers.shutdownNow();
        events.shutdown();
        callbacks.shutdown();
    }

    private void playPcm(byte[] data) {
        var active = bridge;
        if (active == null) {
            throw new CallException(CallErrorCode.AUDIO_NOT_STARTED);
        }
        var pcm = AudioFiles.toCallPcm(data, options.getFfmpegPath());
        stopPlaying();
        active.pcm().clear();
        var current = new Playback();
        playback = current;
        try {
            await(active.connected(), AUDIO_CONNECT_TIMEOUT, CallErrorCode.AUDIO_FAILED,
                CallErrorCode.AUDIO_FAILED.getMessage() + ": " + DETAIL_NOT_CONNECTED);
        } catch (CallException e) {
            if (current.stopped.isDone() || bridge != active) {
                return;
            }
            throw e;
        }
        if (current.stopped.isDone() || bridge != active) {
            return;
        }
        active.pcm().write(pcm);
        CompletableFuture.anyOf(active.pcm().drained(), current.stopped).join();
        if (playback == current) {
            playback = null;
        }
    }

    private void createBridge(CompletableFuture<Void> pending, List<IceServer> iceServers) {
        if (pendingBridge != pending) {
            return;
        }
        try {
            if (engine == null) {
                engine = AudioBridge.Engine.create();
            }
            var created = new AudioBridge[1];
            created[0] = new AudioBridge(engine, iceServers, events, timers, socket::send,
                (samples, rate) -> onRemoteAudio(created[0], samples, rate),
                error -> onBridgeFailed(created[0], error));
            bridge = created[0];
            remoteResampler = null;
            var active = created[0];
            active.connected()
                .thenCompose(ignored -> active.remoteAudio().applyToEither(
                    delay(REMOTE_AUDIO_WAIT), value -> null))
                .thenRun(() -> active.pcm().ready());
            active.start();
        } catch (RuntimeException e) {
            pendingBridge = null;
            teardownBridge(false);
            pending.completeExceptionally(CallErrors.wrap(e));
        }
    }

    private CompletableFuture<Void> delay(Duration duration) {
        var done = new CompletableFuture<Void>();
        timers.schedule(() -> done.complete(null), duration.toMillis(), TimeUnit.MILLISECONDS);
        return done;
    }

    private void onRemoteAudio(AudioBridge source, short[] samples, int rate) {
        if (source != bridge || listeners.isEmpty()) {
            return;
        }
        if (remoteResampler == null || remoteRate != rate) {
            remoteResampler = new Resampler(rate, options.getPcmSampleRate());
            remoteRate = rate;
        }
        var pcm = Resampler.toBytes(remoteResampler.process(samples));
        emit(listener -> listener.onRemotePcm(pcm));
    }

    private void onMessage(String text) {
        JsonNode frame;
        try {
            frame = MAPPER.readTree(text);
        } catch (Exception e) {
            log.warn("Skipping a callsRtc frame that is not JSON");
            return;
        }
        switch (frame.path("type").asText()) {
            case "state" -> onState(MAPPER.convertValue(frame.get("state"), CallState.class));
            case "answer" -> onAnswer(frame.path("answer").path("sdp").asText());
            case "ice-candidate" -> onRemoteCandidate(frame.path("candidate"));
            case "error" -> onServerError(frame.path("message").asText());
            default -> {
            }
        }
    }

    private void onState(CallState next) {
        var previous = state == null ? null : state.getState();
        state = next;
        if (firstState != null) {
            firstState.complete(next);
            firstState = null;
        }
        emit(listener -> listener.onState(next));
        waiters.stream().filter(waiter -> waiter.kind() == next.getState()).forEach(waiter -> waiter.future().complete(next));

        if (next.getState() == CallStateKind.INC_CALL && previous != CallStateKind.INC_CALL && next.getInfo() != null) {
            emit(listener -> listener.onIncomingCall(next.getInfo()));
            return;
        }

        if (previous != null && previous.isActive() && !next.getState().isActive()) {
            resumePending = false;
            rejectPendingBridge(CallErrorCode.CALL_ENDED, DETAIL_CALL_ENDED);
            teardownBridge(true);
            var description = CallErrors.describeEndReason(next.getReason());
            var end = new CallEnd(END_REASON_CALL_ENDED, next.getReason(), description == null ? CALL_ENDED_DESCRIPTION : description);
            emit(listener -> listener.onEndCall(end));
            failWaiters(new CallException(CallErrorCode.CALL_ENDED, end.getDescription(), null, next.getReason(), null), CallStateKind.IDLE);
            return;
        }

        if (resumePending && next.getState().isActive() && bridge == null && pendingBridge == null) {
            resumePending = false;
            CompletableFuture.runAsync(() -> {
                try {
                    startAudioBridge();
                } catch (CallException e) {
                    emitError(e);
                }
            });
        }
    }

    private void onAnswer(String sdp) {
        var active = bridge;
        if (active == null) {
            return;
        }
        try {
            if (!active.setAnswer(sdp)) {
                return;
            }
        } catch (RuntimeException e) {
            if (bridge != active) {
                return;
            }
            var error = CallErrors.wrap(e);
            var pending = pendingBridge;
            pendingBridge = null;
            teardownBridge(false);
            if (pending != null) {
                pending.completeExceptionally(error);
            } else {
                emitError(error);
            }
            return;
        }
        if (bridge == active && pendingBridge != null) {
            var pending = pendingBridge;
            pendingBridge = null;
            pending.complete(null);
        }
    }

    private void onRemoteCandidate(JsonNode candidate) {
        var active = bridge;
        if (active == null) {
            return;
        }
        active.addCandidate(candidate.path("candidate").asText(), candidate.path("sdpMid").asText(null),
            candidate.path("sdpMLineIndex").asInt(0));
    }

    private void onServerError(String message) {
        var error = new CallException(CallErrorCode.CALL_FAILED, CallErrorCode.CALL_FAILED.getMessage() + ": " + message, null, message, null);
        var pending = pendingBridge;
        if (pending != null) {
            pendingBridge = null;
            teardownBridge(false);
            pending.completeExceptionally(error);
            return;
        }
        refusalReported = true;
        emitError(error);
    }

    private void onBridgeFailed(AudioBridge failed, CallException error) {
        if (bridge != failed) {
            return;
        }
        var pending = pendingBridge;
        pendingBridge = null;
        teardownBridge(false);
        if (pending != null) {
            pending.completeExceptionally(error);
        } else {
            emitError(error);
        }
    }

    private void onSocketDisconnect(CallsDisconnect detail) {
        var previous = state == null ? null : state.getState();
        if (previous != null && previous.isActive()) {
            resumePending = bridge != null && !detail.isPermanent();
            rejectPendingBridge(CallErrorCode.CONNECTION_FAILED, DETAIL_CONNECTION_LOST);
            teardownBridge(false);
            if (!resumePending) {
                state = null;
                var end = new CallEnd(END_REASON_CONNECTION_LOST, null, CONNECTION_LOST_DESCRIPTION);
                emit(listener -> listener.onEndCall(end));
                failWaiters(new CallException(CallErrorCode.CONNECTION_FAILED, CONNECTION_LOST_DESCRIPTION), CallStateKind.IDLE);
            }
        } else {
            state = null;
        }
        if (detail.isPermanent()) {
            var error = detail.getError() == null ? new CallException(CallErrorCode.CONNECTION_FAILED) : detail.getError();
            var opening = firstState != null;
            if (firstState != null) {
                firstState.completeExceptionally(error);
                firstState = null;
            }
            failWaiters(error);
            if (!opening && !refusalReported && detail.getError() != null) {
                emitError(detail.getError());
            }
        }
        refusalReported = false;
        emit(listener -> listener.onDisconnect(detail));
    }

    private void rejectPendingBridge(CallErrorCode code, String detail) {
        var pending = pendingBridge;
        pendingBridge = null;
        if (pending != null) {
            pending.completeExceptionally(new CallException(code, CallErrorCode.AUDIO_FAILED.getMessage() + ": " + detail));
        }
    }

    private void teardownBridge(boolean notifyServer) {
        stopPlaying();
        var active = bridge;
        bridge = null;
        remoteResampler = null;
        if (active == null) {
            return;
        }
        if (notifyServer) {
            socket.send("{\"type\":\"stop\"}");
        }
        active.close();
    }

    private void failWaiters(CallException error) {
        failWaiters(error, null);
    }

    private void failWaiters(CallException error, CallStateKind except) {
        waiters.stream().filter(waiter -> waiter.kind() != except).forEach(waiter -> waiter.future().completeExceptionally(error));
    }

    private void emit(Consumer<CallsListener> event) {
        if (callbacks.isShutdown()) {
            return;
        }
        callbacks.execute(() -> listeners.forEach(listener -> {
            try {
                event.accept(listener);
            } catch (RuntimeException e) {
                log.error("Calls listener failed", e);
            }
        }));
    }

    private void emitError(CallException error) {
        if (listeners.isEmpty()) {
            log.warn("Calls connection error: {} {}", error.getCode(), error.getMessage());
            return;
        }
        emit(listener -> listener.onError(error));
    }

    private void ensureOpen() {
        if (closed) {
            throw new CallException(CallErrorCode.CONNECTION_FAILED, "The calls connection is closed, create a new one with connectCalls()");
        }
    }

    private <T> T call(Supplier<T> action) {
        try {
            return events.submit(action::get).get();
        } catch (ExecutionException e) {
            throw CallErrors.wrap(e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CallException(CallErrorCode.CALL_FAILED, "Interrupted");
        } catch (RejectedExecutionException e) {
            throw new CallException(CallErrorCode.CONNECTION_FAILED, "The calls connection is closed");
        }
    }

    private void run(Runnable action) {
        call(() -> {
            action.run();
            return null;
        });
    }

    private static <T> T await(CompletableFuture<T> future, Duration timeout, CallErrorCode timeoutCode, String timeoutMessage) {
        try {
            return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new CallException(timeoutCode, timeoutMessage);
        } catch (ExecutionException e) {
            throw CallErrors.wrap(e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CallException(CallErrorCode.CALL_FAILED, "Interrupted");
        }
    }

    private static ThreadFactory daemon(String name) {
        return runnable -> {
            var thread = new Thread(runnable, name);
            thread.setDaemon(true);
            return thread;
        };
    }
}
