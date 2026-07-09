package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.ForwardMessagesReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ForwardMessagesExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        new ForwardMessagesExample().forwardMessagesExample(greenApi);
    }

    private void forwardMessagesExample(GreenApi greenApi) {
        var response = greenApi.sending.forwardMessages(
            ForwardMessagesReq.builder()
                .chatId("11001234567@c.us")
                .chatIdFrom("11009876543@c.us")
                .messages(List.of("BAE5F4886F6F2D05"))
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Forwarded message ids: " + Objects.requireNonNull(response.getBody()).getMessages());
        } else {
            log.warn("Forward failed, status: " + response.getStatusCode());
        }
    }
}
