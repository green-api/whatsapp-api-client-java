package com.greenapi;

import com.greenapi.pkg.api.webhook.WebhookConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebhookExample {
    public static void main(String[] args) {
        var context = SpringApplication.run(WebhookExample.class, args);

        var webhookConsumer = (WebhookConsumer) context.getBean("webhookConsumer");
        webhookConsumer.start();
    }
}
