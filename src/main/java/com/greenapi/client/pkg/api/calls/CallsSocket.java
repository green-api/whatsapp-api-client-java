package com.greenapi.client.pkg.api.calls;

import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallsDisconnect;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocketHandshakeException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

class CallsSocket {
    private final URI uri;
    private final Executor events;
    private final ScheduledExecutorService timers;
    private final Consumer<String> onMessage;
    private final Runnable onConnect;
    private final Consumer<CallsDisconnect> onDisconnect;
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).build();
    private WebSocket socket;
    private CompletableFuture<WebSocket> sending = CompletableFuture.completedFuture(null);
    private CompletableFuture<Void> pendingOpen;
    private ScheduledFuture<?> heartbeat;
    private ScheduledFuture<?> reconnect;
    private Duration reconnectDelay = RECONNECT_FIRST_DELAY;
    private volatile boolean alive = true;
    private boolean closedByUser;
    private int generation;

    CallsSocket(String url, Executor events, ScheduledExecutorService timers, Consumer<String> onMessage,
                Runnable onConnect, Consumer<CallsDisconnect> onDisconnect) {
        this.uri = URI.create(url);
        this.events = events;
        this.timers = timers;
        this.onMessage = onMessage;
        this.onConnect = onConnect;
        this.onDisconnect = onDisconnect;
    }

    CompletableFuture<Void> open() {
        if (socket != null) {
            return CompletableFuture.completedFuture(null);
        }
        if (pendingOpen != null) {
            return pendingOpen;
        }
        closedByUser = false;
        pendingOpen = new CompletableFuture<>();
        var opening = pendingOpen;
        connect();
        return opening;
    }

    boolean isOpen() {
        return socket != null;
    }

    boolean send(String text) {
        var current = socket;
        if (current == null) {
            return false;
        }
        sending = sending.handle((ignored, error) -> null)
            .thenCompose(ignored -> current.sendText(text, true));
        return true;
    }

    void close() {
        closedByUser = true;
        generation++;
        cancel(reconnect);
        cancel(heartbeat);
        var current = socket;
        socket = null;
        if (current != null) {
            current.sendClose(CLOSE_NORMAL, "").whenComplete((ignored, error) -> current.abort());
        }
        if (pendingOpen != null) {
            pendingOpen.completeExceptionally(new CallException(CallErrorCode.CONNECTION_FAILED, "The calls connection was closed"));
            pendingOpen = null;
        }
    }

    private void connect() {
        var attempt = ++generation;
        client.newWebSocketBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .buildAsync(uri, new Listener(attempt))
            .whenCompleteAsync((webSocket, error) -> {
                if (attempt != generation) {
                    if (webSocket != null) {
                        webSocket.abort();
                    }
                    return;
                }
                if (error != null) {
                    onConnectFailed(error);
                }
            }, events);
    }

    private void onOpened(WebSocket webSocket, int attempt) {
        if (attempt != generation || closedByUser) {
            webSocket.abort();
            return;
        }
        socket = webSocket;
        sending = CompletableFuture.completedFuture(webSocket);
        reconnectDelay = RECONNECT_FIRST_DELAY;
        alive = true;
        cancel(heartbeat);
        heartbeat = timers.scheduleAtFixedRate(() -> events.execute(() -> ping(webSocket)),
            HEARTBEAT_INTERVAL.toMillis(), HEARTBEAT_INTERVAL.toMillis(), TimeUnit.MILLISECONDS);
        if (pendingOpen != null) {
            pendingOpen.complete(null);
            pendingOpen = null;
        }
        onConnect.run();
    }

    private void ping(WebSocket webSocket) {
        if (webSocket != socket) {
            return;
        }
        if (!alive) {
            webSocket.abort();
            onClosed(webSocket, CLOSE_ABNORMAL, "No pong from the calls server");
            return;
        }
        alive = false;
        webSocket.sendPing(ByteBuffer.allocate(0));
    }

    private void onConnectFailed(Throwable error) {
        var cause = error.getCause() != null ? error.getCause() : error;
        if (cause instanceof WebSocketHandshakeException handshake) {
            var status = handshake.getResponse().statusCode();
            var refusal = CallErrors.fromHandshake(status, cause);
            if (refusal.getCode() != CallErrorCode.CONNECTION_FAILED) {
                disconnected(new CallsDisconnect(refusal.getMessage(), status, true, refusal));
                return;
            }
        }
        if (pendingOpen != null) {
            var failure = new CallException(CallErrorCode.CONNECTION_FAILED,
                "Could not connect to the calls server: " + cause.getMessage(), null, null, cause);
            disconnected(new CallsDisconnect(failure.getMessage(), null, true, failure));
            return;
        }
        disconnected(new CallsDisconnect(cause.getMessage(), null, false, null));
        scheduleReconnect();
    }

    private void onClosed(WebSocket webSocket, int code, String reason) {
        if (webSocket != socket) {
            return;
        }
        socket = null;
        cancel(heartbeat);
        if (closedByUser) {
            return;
        }
        if (CallErrors.isPermanentClose(code)) {
            var refusal = CallErrors.fromClose(code, reason);
            disconnected(new CallsDisconnect(refusal.getMessage(), code, true, refusal));
            return;
        }
        disconnected(new CallsDisconnect(reason.isEmpty() ? "Connection lost (code " + code + ")" : reason, code, false, null));
        scheduleReconnect();
    }

    private void scheduleReconnect() {
        var delay = reconnectDelay;
        reconnectDelay = delay.multipliedBy(2).compareTo(RECONNECT_MAX_DELAY) > 0 ? RECONNECT_MAX_DELAY : delay.multipliedBy(2);
        cancel(reconnect);
        reconnect = timers.schedule(() -> events.execute(() -> {
            if (!closedByUser) {
                connect();
            }
        }), delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    private void disconnected(CallsDisconnect detail) {
        if (pendingOpen != null && detail.getError() != null) {
            pendingOpen.completeExceptionally(detail.getError());
            pendingOpen = null;
        }
        onDisconnect.accept(detail);
    }

    private static void cancel(ScheduledFuture<?> task) {
        if (task != null) {
            task.cancel(false);
        }
    }

    private class Listener implements WebSocket.Listener {
        private final int attempt;
        private final StringBuilder text = new StringBuilder();

        Listener(int attempt) {
            this.attempt = attempt;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            events.execute(() -> onOpened(webSocket, attempt));
            webSocket.request(1);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            text.append(data);
            if (last) {
                var message = text.toString();
                text.setLength(0);
                events.execute(() -> {
                    if (webSocket == socket) {
                        onMessage.accept(message);
                    }
                });
            }
            webSocket.request(1);
            return null;
        }

        @Override
        public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
            alive = true;
            webSocket.request(1);
            return null;
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            events.execute(() -> onClosed(webSocket, statusCode, reason == null ? "" : reason));
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            events.execute(() -> onClosed(webSocket, CLOSE_ABNORMAL, error.getMessage() == null ? "" : error.getMessage()));
        }
    }
}
