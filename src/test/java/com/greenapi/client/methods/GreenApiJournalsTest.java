package com.greenapi.client.methods;

import com.greenapi.pkg.models.request.GetChatHistoryReq;
import com.greenapi.pkg.models.request.MessageReq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiJournalsTest extends GreenApiTest {
    @Test
    void getChatHistory() {
        var dto = GetChatHistoryReq.builder()
            .chatId(chatId)
            .count(3)
            .build();

        var response = greenApi.journals.getChatHistory(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getMessage() {
        var dto = MessageReq.builder()
            .chatId(chatId)
            .idMessage(messageId)
            .build();

        var response = greenApi.journals.getMessage(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void lastIncomingMessages() {
        var response = greenApi.journals.lastIncomingMessages(3);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void lastOutgoingMessages() {
        var response = greenApi.journals.lastOutgoingMessages(3);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}