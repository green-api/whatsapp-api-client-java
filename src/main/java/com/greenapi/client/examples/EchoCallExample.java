package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.api.calls.CallsListener;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallEnd;
import com.greenapi.client.pkg.models.calls.CallInfo;
import com.greenapi.client.pkg.models.calls.CallsConnectionOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
public class EchoCallExample {
    private static final long ECHO_DELAY_MS = 1500;

    public static void main(String[] args) throws InterruptedException {
        var greenApi = new GreenApi(
            new RestTemplate(),
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var calls = greenApi.calls.connectCalls(CallsConnectionOptions.builder().pcmSampleRate(24000).build());
        var worker = Executors.newSingleThreadExecutor();
        var echo = Executors.newSingleThreadScheduledExecutor();

        calls.addListener(new CallsListener() {
            @Override
            public void onIncomingCall(CallInfo info) {
                log.info("Incoming call from " + info.getWid() + ", talk and the bot repeats after " + ECHO_DELAY_MS + " ms");
                worker.execute(() -> {
                    try {
                        greenApi.calls.callsAccept();
                        calls.startAudioBridge();
                    } catch (CallException e) {
                        log.warn("Call failed: " + e.getCode() + " " + e.getMessage());
                    }
                });
            }

            @Override
            public void onRemotePcm(byte[] pcm) {
                echo.schedule(() -> {
                    if (calls.hasAudioBridge()) {
                        calls.sendPcm(pcm);
                    }
                }, ECHO_DELAY_MS, TimeUnit.MILLISECONDS);
            }

            @Override
            public void onEndCall(CallEnd detail) {
                log.info("Call ended: " + detail.getDescription());
            }
        });

        calls.open();
        log.info("Waiting for an incoming call, Ctrl+C to exit");
        Thread.currentThread().join();
    }
}
