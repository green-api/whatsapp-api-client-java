package com.greenapi.client.methods;

import com.greenapi.client.GreenApiClient;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Log4j2
public class ExampleTest extends GreenApiTest {

    @Test
    void createGroupAndSendMessage_defaultInstanceFromBean() {
        examples.createGroupAndSendMessage(greenApiClient, chatId);
    }

    @Test
    void createGroupAndSendMessage_newInstanceFromConstructor() {
        var restTemplate2 = new RestTemplateBuilder().build();

        var clientFromConstructor = new GreenApiClient(restTemplate2,
            "https://media.greenapi.com",
            "https://api.greenapi.com",
            instanceId,
            instanceToken);

        examples.createGroupAndSendMessage(clientFromConstructor, "5491126383402@c.us");
    }
}
