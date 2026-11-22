package com.greenapi.client.pkg.api.calls;

import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallEnd;
import com.greenapi.client.pkg.models.calls.CallInfo;
import com.greenapi.client.pkg.models.calls.CallState;
import com.greenapi.client.pkg.models.calls.CallsDisconnect;

/**
 * Events of the calls connection. All methods run one after another on one thread of the connection,
 * so keep them short and start long work (answering, playing audio) on your own thread.
 */
public interface CallsListener {
    default void onConnect() {
    }

    default void onDisconnect(CallsDisconnect detail) {
    }

    default void onState(CallState state) {
    }

    default void onIncomingCall(CallInfo info) {
    }

    default void onEndCall(CallEnd detail) {
    }

    /**
     * The other side's audio: 16-bit little-endian mono PCM at pcmSampleRate, in real time.
     */
    default void onRemotePcm(byte[] pcm) {
    }

    default void onError(CallException error) {
    }
}
