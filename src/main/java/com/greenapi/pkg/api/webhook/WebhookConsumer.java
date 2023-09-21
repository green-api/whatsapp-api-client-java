package com.greenapi.pkg.api.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenapi.pkg.api.GreenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class WebhookConsumer {

    private final GreenApi greenApi;
    private final WebhookTypeHandler webhookTypeHandler = new WebhookTypeHandler();

    private boolean running = true;

    public void start() {
        running = true;

        while (running) {
            var response = greenApi.receiving.receiveNotification();

            if (Objects.equals(response.getBody(), "null")) {
                log.info("receiveNotification timeout");
            } else {
                var notification = webhookTypeHandler.get(response.getBody());
                if (notification == null) {
                    throw new RuntimeException("Can't map webhook from json");
                }

                log.info("new webhook received: " + notification);

                greenApi.receiving.deleteNotification(notification.getReceiptId());
            }
        }
    }

    public void stop() {
        running = false;
    }
}
