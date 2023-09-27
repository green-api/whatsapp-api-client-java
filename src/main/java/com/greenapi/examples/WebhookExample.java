package com.greenapi.examples;

import com.greenapi.pkg.api.webhook.WebhookConsumer;
import org.springframework.boot.SpringApplication;

public class WebhookExample {
    public void webhookExample(String[] args) {
        var context = SpringApplication.run(WebhookExample.class, args);

        var webhookConsumer = (WebhookConsumer) context.getBean("webhookConsumer");
        webhookConsumer.start(notification -> System.out.println("New webhook received: " + notification));
    }
}
