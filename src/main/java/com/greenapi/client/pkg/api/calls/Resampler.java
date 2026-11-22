package com.greenapi.client.pkg.api.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class Resampler {
    private final int fromRate;
    private final int toRate;
    private double position;
    private short previous;
    private boolean started;

    Resampler(int fromRate, int toRate) {
        this.fromRate = fromRate;
        this.toRate = toRate;
    }

    static short[] toSamples(byte[] pcm, int length) {
        var samples = new short[length / 2];
        ByteBuffer.wrap(pcm, 0, length).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
        return samples;
    }

    static byte[] toBytes(short[] samples) {
        var buffer = ByteBuffer.allocate(samples.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        buffer.asShortBuffer().put(samples);
        return buffer.array();
    }

    static short[] toMono(short[] interleaved, int channels) {
        if (channels <= 1) {
            return interleaved;
        }
        var mono = new short[interleaved.length / channels];
        for (int frame = 0; frame < mono.length; frame++) {
            int sum = 0;
            for (int channel = 0; channel < channels; channel++) {
                sum += interleaved[frame * channels + channel];
            }
            mono[frame] = (short) (sum / channels);
        }
        return mono;
    }

    short[] process(short[] input) {
        if (fromRate == toRate || input.length == 0) {
            return input;
        }
        if (fromRate > toRate && fromRate % toRate == 0) {
            return decimate(input, fromRate / toRate);
        }
        return interpolate(input);
    }

    void reset() {
        position = 0;
        started = false;
    }

    private short[] decimate(short[] input, int factor) {
        var output = new short[input.length / factor];
        for (int i = 0; i < output.length; i++) {
            int sum = 0;
            for (int j = 0; j < factor; j++) {
                sum += input[i * factor + j];
            }
            output[i] = (short) (sum / factor);
        }
        return output;
    }

    private short[] interpolate(short[] input) {
        if (!started) {
            previous = input[0];
            started = true;
        }
        var step = (double) fromRate / toRate;
        var capacity = (int) Math.ceil((input.length + 1) / step) + 1;
        var output = new short[capacity];
        int count = 0;
        while (position < input.length) {
            var index = (int) Math.floor(position);
            var fraction = position - index;
            var left = index == 0 ? previous : input[index - 1];
            var right = input[index];
            output[count++] = (short) Math.round(left + (right - left) * fraction);
            position += step;
        }
        position -= input.length;
        previous = input[input.length - 1];
        var result = new short[count];
        System.arraycopy(output, 0, result, 0, count);
        return result;
    }
}
