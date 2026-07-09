package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.CustomPreview;
import com.greenapi.client.pkg.models.request.OutgoingMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Log4j2
public class SendMessageWithPreviewExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new SendMessageWithPreviewExample();
        example.sendWithTypingExample(greenApi);
        example.sendWithCustomPreviewExample(greenApi);
    }

    /**
     * Sends a text message with a 4-second typing indicator shown to the recipient beforehand.
     */
    private void sendWithTypingExample(GreenApi greenApi) {
        var response = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("11001234567@c.us")
                .message("Hello! I was typing this for a while...")
                .typingTime(4000)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Message sent with typing indicator, id: "
                + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("Message sending failed, status: " + response.getStatusCode());
        }
    }

    /**
     * Sends a text message with a custom link preview (title, description, thumbnail).
     * The preview overrides the default one auto-generated from the link.
     */
    private void sendWithCustomPreviewExample(GreenApi greenApi) {
        var customPreview = CustomPreview.builder()
            .title("GREEN-API Documentation")
            .description("Full WhatsApp API reference")
            .link("green-api.com")
            .urlFile("https://green-api.com/logo.png")
            .build();

        var response = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("11001234567@c.us")
                .message("Check out the docs: https://green-api.com/docs/api/")
                .linkPreview(true)
                .typePreview("large")
                .customPreview(customPreview)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Message with custom preview sent, id: "
                + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("Message sending failed, status: " + response.getStatusCode());
        }
    }
}
