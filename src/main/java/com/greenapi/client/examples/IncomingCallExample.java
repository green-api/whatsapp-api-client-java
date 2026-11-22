package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.api.calls.CallsConnection;
import com.greenapi.client.pkg.api.calls.CallsListener;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallEnd;
import com.greenapi.client.pkg.models.calls.CallInfo;
import com.greenapi.client.pkg.models.calls.CallState;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.util.concurrent.Executors;

@Log4j2
public class IncomingCallExample {

    public static void main(String[] args) throws InterruptedException {
        var greenApi = new GreenApi(
            new RestTemplate(),
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var calls = greenApi.calls.connectCalls();
        var worker = Executors.newSingleThreadExecutor();

        calls.addListener(new CallsListener() {
            @Override
            public void onState(CallState state) {
                log.info("Call state: " + state.getState().getValue());
            }

            @Override
            public void onIncomingCall(CallInfo info) {
                log.info("Incoming call from " + (info.getName() != null ? info.getName() : info.getWid()));
                worker.execute(() -> answer(greenApi, calls));
            }

            @Override
            public void onEndCall(CallEnd detail) {
                log.info("Call ended: " + detail.getDescription());
            }

            @Override
            public void onError(CallException error) {
                log.warn("Call error: " + error.getCode() + " " + error.getMessage());
            }
        });

        calls.open();
        log.info("Waiting for an incoming call, Ctrl+C to exit");
        Thread.currentThread().join();
    }

    private static void answer(GreenApi greenApi, CallsConnection calls) {
        try {
            greenApi.calls.callsAccept();
            calls.startAudioBridge();
            calls.play(Path.of("hello.wav"));
            greenApi.calls.callsHangUp();
        } catch (CallException e) {
            log.warn("Call failed: " + e.getCode() + " " + e.getMessage());
        }
    }
}
