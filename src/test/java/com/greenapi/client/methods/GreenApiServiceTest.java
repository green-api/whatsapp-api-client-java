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

        var response = greenApiClient.service.checkWhatsapp(phoneNumber);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAvatar() {
        var chatId = "79851150769@c.us";

        var response = greenApiClient.service.getAvatar(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getContacts() {
        var response = greenApiClient.service.getContacts();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getContactInfo() {
        var chatId = "79851150769@c.us";

        var response = greenApiClient.service.getContactInfo(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*@Test
    void deleteMessage() {
        var dto = MessageReq.builder()
            .chatId("79851150769@c.us")
            .idMessage("BAE5F74BAE5B79C9")
            .build();

        var response = greenApiClient.service.deleteMessage(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    void archiveChat() {
        var chatId = "79851150769@c.us";

        var response = greenApiClient.service.archiveChat(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void unarchiveChat() {
        var chatId = "79851150769@c.us";

        var response = greenApiClient.service.unarchiveChat(chatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setDisappearingChat() {
        var chatId = "79851150769@c.us";
        var expiration = 0L;

        var response = greenApiClient.service.setDisappearingChat(chatId, expiration);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}