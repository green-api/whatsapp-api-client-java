package com.greenapi.client.pkg.api.calls;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

class PcmSender {
    private static final int FRAME_BYTES = FRAME_SAMPLES * BYTES_PER_SAMPLE;
    private static final long FRAME_NANOS = FRAME_MS * 1_000_000L;

    private final Consumer<byte[]> push;
    private final ArrayDeque<byte[]> queue = new ArrayDeque<>();
    private final List<CompletableFuture<Void>> drainWaiters = new ArrayList<>();
    private final Thread thread;
    private final byte[] silence = new byte[FRAME_BYTES];
    private int queuedBytes;
    private int headOffset;
    private volatile boolean running = true;
    private volatile boolean ready;

    PcmSender(Consumer<byte[]> push) {
        this.push = push;
        this.thread = new Thread(this::run, "green-api-calls-audio");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    synchronized void write(byte[] pcm) {
        if (!running || pcm.length == 0) {
            return;
        }
        queue.add(pcm);
        queuedBytes += pcm.length;
    }

    synchronized void clear() {
        queue.clear();
        queuedBytes = 0;
        headOffset = 0;
        resolveDrained();
    }

    void ready() {
        ready = true;
    }

    synchronized int pendingMs() {
        return queuedBytes / BYTES_PER_SAMPLE / SAMPLES_PER_MS;
    }

    synchronized CompletableFuture<Void> drained() {
        if (queuedBytes == 0 || !running) {
            return CompletableFuture.completedFuture(null);
        }
        var waiter = new CompletableFuture<Void>();
        drainWaiters.add(waiter);
        return waiter;
    }

    void stop() {
        running = false;
        LockSupport.unpark(thread);
        if (Thread.currentThread() != thread) {
            try {
                thread.join(STOP_WAIT.toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        clear();
    }

    private void run() {
        var start = System.nanoTime();
        long frame = 0;
        while (running) {
            var due = start + frame * FRAME_NANOS;
            var now = System.nanoTime();
            if (now - due > MAX_SEND_LAG.toNanos()) {
                start = now;
                frame = 0;
                due = now;
            }
            if (due > now) {
                LockSupport.parkNanos(due - now);
                continue;
            }
            byte[] next = take();
            try {
                push.accept(next);
            } catch (RuntimeException ignored) {
                running = false;
            }
            frame++;
        }
    }

    private synchronized byte[] take() {
        if (!ready) {
            return silence;
        }
        if (queuedBytes == 0) {
            resolveDrained();
            return silence;
        }
        var frame = new byte[FRAME_BYTES];
        int filled = 0;
        while (filled < FRAME_BYTES && !queue.isEmpty()) {
            var head = queue.peek();
            var size = Math.min(head.length - headOffset, FRAME_BYTES - filled);
            System.arraycopy(head, headOffset, frame, filled, size);
            filled += size;
            headOffset += size;
            if (headOffset == head.length) {
                queue.poll();
                headOffset = 0;
            }
        }
        queuedBytes -= filled;
        if (queuedBytes == 0) {
            resolveDrained();
        }
        return frame;
    }

    private void resolveDrained() {
        drainWaiters.forEach(waiter -> waiter.complete(null));
        drainWaiters.clear();
    }
}
