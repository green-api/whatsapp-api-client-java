package com.greenapi.client.pkg.api.webhook;

import com.greenapi.client.pkg.api.GreenApi;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class WebhookConsumer {

    private final GreenApi greenApi;
    private final NotificationMapper notificationMapper;

    private boolean running = true;

    @SneakyThrows
    public void start(WebhookHandler webhookHandler) {
        running = true;

        while (running) {
            try {
                var response = greenApi.receiving.receiveNotification();

                if (Objects.equals(response.getBody(), "null")) {
                    log.info("receiveNotification timeout");

                } else {
                    var notification = notificationMapper.get(response.getBody());

                    if (notification.getBody() == null) {
                        log.error("Can't map webhook from json!");
                        greenApi.receiving.deleteNotification(notification.getReceiptId());
                    } else {
                        webhookHandler.handle(notification);
                        greenApi.receiving.deleteNotification(notification.getReceiptId());
                    }
                }
            } catch (Exception e) {
                log.error("UnexpectedError: " + e);
                Thread.sleep(5000);
            }
        }
    }

    public void stop() {
        if (running) {
            running = false;
        } else {
            log.warn("webhookConsumer already stopped");
        }
    }
}
