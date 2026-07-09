package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.SendTypingReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SendTypingExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new SendTypingExample();
        example.sendTypingExample(greenApi);
        example.sendRecordingExample(greenApi);
    }

    /**
     * Shows a "typing..." indicator in the chat for 3 seconds.
     */
    private void sendTypingExample(GreenApi greenApi) {
        var response = greenApi.service.sendTyping(
            SendTypingReq.builder()
                .chatId("11001234567@c.us")
                .typingTime(3000)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Typing indicator sent");
        } else {
            log.warn("Failed to send typing indicator, status: " + response.getStatusCode());
        }
    }

    /**
     * Shows a "recording audio..." indicator in the chat for 5 seconds.
     */
    private void sendRecordingExample(GreenApi greenApi) {
        var response = greenApi.service.sendTyping(
            SendTypingReq.builder()
                .chatId("11001234567@c.us")
                .typingTime(5000)
                .typingType("recording")
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Recording indicator sent");
        } else {
            log.warn("Failed to send recording indicator, status: " + response.getStatusCode());
        }
    }
}
