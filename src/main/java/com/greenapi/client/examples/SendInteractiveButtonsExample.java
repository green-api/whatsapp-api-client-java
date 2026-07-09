package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.InteractiveButton;
import com.greenapi.client.pkg.models.request.InteractiveReplyButton;
import com.greenapi.client.pkg.models.request.OutgoingInteractiveButtons;
import com.greenapi.client.pkg.models.request.OutgoingInteractiveButtonsReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Log4j2
public class SendInteractiveButtonsExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new SendInteractiveButtonsExample();
        example.sendInteractiveButtonsExample(greenApi);
        example.sendInteractiveButtonsReplyExample(greenApi);
    }

    /**
     * Sends a message with action buttons (url, call, copy).
     * Maximum 3 buttons per message.
     */
    private void sendInteractiveButtonsExample(GreenApi greenApi) {
        var buttons = List.of(
            InteractiveButton.builder()
                .type("url")
                .buttonId("1")
                .buttonText("Visit site")
                .url("https://green-api.com")
                .build(),
            InteractiveButton.builder()
                .type("call")
                .buttonId("2")
                .buttonText("Call us")
                .phoneNumber("11001234567")
                .build()
        );

        var response = greenApi.sending.sendInteractiveButtons(
            OutgoingInteractiveButtons.builder()
                .chatId("11001234567@c.us")
                .header("Support")
                .body("Choose an action")
                .footer("GREEN-API")
                .buttons(buttons)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Interactive buttons sent, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("Sending failed, status: " + response.getStatusCode());
        }
    }

    /**
     * Sends a message with reply buttons that send their text back into the chat when clicked.
     * Maximum 3 buttons per message. Beta feature.
     */
    private void sendInteractiveButtonsReplyExample(GreenApi greenApi) {
        var buttons = List.of(
            InteractiveReplyButton.builder()
                .buttonId("1")
                .buttonText("Yes")
                .build(),
            InteractiveReplyButton.builder()
                .buttonId("2")
                .buttonText("No")
                .build()
        );

        var response = greenApi.sending.sendInteractiveButtonsReply(
            OutgoingInteractiveButtonsReply.builder()
                .chatId("11001234567@c.us")
                .body("Confirm your order?")
                .footer("Order #12345")
                .buttons(buttons)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Reply buttons sent, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("Sending failed, status: " + response.getStatusCode());
        }
    }
}
