package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.webhook.WebhookConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Use "main" instead "webhookExample" in real code.
 */
@SpringBootApplication
public class WebhookExample {
    public static void webhookExample(String[] args) {
        var context = SpringApplication.run(WebhookExample.class, args);

        var webhookConsumer = context.getBean(WebhookConsumer.class);
        webhookConsumer.start(System.out::println);
    }
}
