package com.greenapi.client.methods;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiServiceTest extends GreenApiTest {

    @Test
    void checkWhatsapp() {
        var phoneNumber = 79851150769L;

        var response = greenApi.service.checkWhatsapp(phoneNumber);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAvatar() {
        var response = greenApi.service.getAvatar(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getContacts() {
        var response = greenApi.service.getContacts();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getContactInfo() {
        var response = greenApi.service.getContactInfo(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*@Test
    void deleteMessage() {
        var dto = MessageReq.builder()
            .chatId(chatId)
            .idMessage(messageId)
            .build();

        var response = greenApiClient.service.deleteMessage(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    void archiveChat() {
        var response = greenApi.service.archiveChat(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void unarchiveChat() {
        var response = greenApi.service.unarchiveChat(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setDisappearingChat() {
        var response = greenApi.service.setDisappearingChat(chatId, 0L);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}