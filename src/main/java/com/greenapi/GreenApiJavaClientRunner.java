package com.greenapi;

import com.greenapi.client.GreenApiClient;
import com.greenapi.examples.Examples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenApiJavaClientRunner {
    public static void main(String[] args) {
        SpringApplication.run(GreenApiJavaClientRunner.class, args);
    }
}
