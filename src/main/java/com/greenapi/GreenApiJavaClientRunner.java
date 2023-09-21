package com.greenapi;

import com.greenapi.pkg.api.webhook.WebhookConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GreenApiJavaClientRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(GreenApiJavaClientRunner.class, args);

        var webhookConsumer = (WebhookConsumer) context.getBean("webhookConsumer");
        webhookConsumer.start();
    }
}
