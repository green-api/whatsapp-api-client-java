package com.greenapi.client.methods;

import com.greenapi.client.dto.request.MessageReq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiReceivingTest extends GreenApiTest {
    @Test
    void receiveNotification() {
        var response = greenApiClient.receiving.receiveNotification();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteNotification() {
        var receiptId = 1;

        var response = greenApiClient.receiving.deleteNotification(receiptId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void downloadFile() {
        var dto = MessageReq.builder()
            .chatId("79851150769@c.us")
            .idMessage("BAE5F74BAE5B79C9")
            .build();

        var response = greenApiClient.receiving.downloadFile(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}