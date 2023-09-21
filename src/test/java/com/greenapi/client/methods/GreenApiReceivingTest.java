package com.greenapi.client.methods;

import com.greenapi.pkg.models.request.MessageReq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiReceivingTest extends GreenApiTest {
    @Test
    void receiveNotification() {
        var response = greenApi.receiving.receiveNotification();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteNotification() {
        var receiptId = 1;

        var response = greenApi.receiving.deleteNotification(receiptId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void downloadFile() {
        var dto = MessageReq.builder()
            .chatId(chatId)
            .idMessage(fileMessageId)
            .build();

        var response = greenApi.receiving.downloadFile(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}