package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Log4j2
public class CallsJournalExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new CallsJournalExample();
        example.lastIncomingCallsExample(greenApi);
        example.lastOutgoingCallsExample(greenApi);
    }

    /**
     * Returns incoming calls for the last 60 minutes.
     * Beta feature - requires incomingWebhook and incomingCallWebhook enabled in settings.
     */
    private void lastIncomingCallsExample(GreenApi greenApi) {
        var response = greenApi.journals.lastIncomingCalls(60);

        if (response.getStatusCode().is2xxSuccessful()) {
            var calls = Objects.requireNonNull(response.getBody());
            log.info("Incoming calls (last 60 min): " + calls.size());
            calls.forEach(call -> log.info(
                "From: " + call.getChatId() +
                " | video: " + call.getIsVideo() +
                " | status: " + call.getStatus()));
        } else {
            log.warn("Failed to get incoming calls, status: " + response.getStatusCode());
        }
    }

    /**
     * Returns outgoing calls for the last 24 hours (default period).
     * Beta feature.
     */
    private void lastOutgoingCallsExample(GreenApi greenApi) {
        var response = greenApi.journals.lastOutgoingCalls();

        if (response.getStatusCode().is2xxSuccessful()) {
            var calls = Objects.requireNonNull(response.getBody());
            log.info("Outgoing calls (last 24h): " + calls.size());
            calls.forEach(call -> log.info(
                "To: " + call.getChatId() +
                " | duration: " + call.getDuration() + "s" +
                " | status: " + call.getStatus()));
        } else {
            log.warn("Failed to get outgoing calls, status: " + response.getStatusCode());
        }
    }
}
