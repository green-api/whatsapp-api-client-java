package com.greenapi.client.methods;

import com.greenapi.client.dto.request.GetChatHistoryReq;
import com.greenapi.client.dto.request.MessageReq;
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

        var response = greenApiClient.journals.getChatHistory(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getMessage() {
        var dto = MessageReq.builder()
            .chatId(chatId)
            .idMessage(messageId)
            .build();

        var response = greenApiClient.journals.getMessage(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void lastIncomingMessages() {
        var response = greenApiClient.journals.lastIncomingMessages(3);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void lastOutgoingMessages() {
        var response = greenApiClient.journals.lastOutgoingMessages(3);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}