package com.greenapi;

import com.greenapi.pkg.api.webhook.WebhookConsumer;
import com.greenapi.pkg.api.webhook.WebhookHandler;
import com.greenapi.pkg.models.Contact;
import com.greenapi.pkg.models.notifications.Notification;
import com.greenapi.pkg.models.notifications.messages.messageData.ContactMessageData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebhookExample {
    public static void main(String[] args) {
        var context = SpringApplication.run(WebhookExample.class, args);

        var webhookConsumer = (WebhookConsumer) context.getBean("webhookConsumer");
        webhookConsumer.start(notification -> System.out.println("New webhook received: " + notification));
    }
}
