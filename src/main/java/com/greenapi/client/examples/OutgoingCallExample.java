package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallStateKind;
import com.greenapi.client.pkg.models.request.CallsDialReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;

@Log4j2
public class OutgoingCallExample {

    public static void main(String[] args) {
        var greenApi = new GreenApi(
            new RestTemplate(),
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        try (var calls = greenApi.calls.connectCalls()) {
            calls.open();
            var response = greenApi.calls.callsDial(new CallsDialReq("79001234567@c.us"));
            log.info("Calling, callId: " + Objects.requireNonNull(response.getBody()).getCallId());

            calls.startAudioBridge();
            calls.waitFor(CallStateKind.ON_CALL, Duration.ofSeconds(60));
            calls.play(Path.of("hello.wav"));
            greenApi.calls.callsHangUp();
        } catch (CallException e) {
            log.warn("Call failed: " + e.getCode() + " " + e.getMessage());
        }
    }
}
