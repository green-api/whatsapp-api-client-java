package com.greenapi.client.methods;

import com.greenapi.pkg.models.request.MessageReq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiMarkingTest extends GreenApiTest {

    @Test
    void readChat() {
        var dto = MessageReq.builder()
            .chatId(chatId)
            .idMessage(messageId)
            .build();

        var response = greenApi.marking.readChat(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}