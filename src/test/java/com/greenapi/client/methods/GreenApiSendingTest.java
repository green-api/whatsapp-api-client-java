package com.greenapi.client.methods;

import com.greenapi.client.domain.Contact;
import com.greenapi.client.dto.request.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

@Log4j2
class GreenApiSendingTest extends GreenApiTest {
    @Test
    void sendMessage() {
        var textMsg = OutgoingMessage.builder()
            .chatId("79851150769@c.us")
            .message("TEST")
            .build();

        var response = greenApiClient.sending.sendMessage(textMsg);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void sendContact() {
        var contactMsg = OutgoingContact.builder()
            .chatId("79851150769@c.us")
            .contact(Contact.builder()
                .phoneContact(79851150769L)
                .firstName("TEST")
                .lastName("TEST")
                .company("TEST")
                .build())
            .build();

        var response = greenApiClient.sending.sendContact(contactMsg);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void sendFileByUpload() throws IOException {
        var file = new File("/Users/kocherov/Desktop/avatarExample.jpeg");
        var fileByUploadMsg = OutgoingFileByUpload.builder()
            .chatId("79851150769@c.us")
            .fileName(file.getName())
            .file(file)
            .build();

        var response = greenApiClient.sending.sendFileByUpload(fileByUploadMsg);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void uploadFile() throws IOException {
        var file = new File("/Users/kocherov/Desktop/avatarExample.jpeg");

        var response = greenApiClient.sending.uploadFile(file);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void sendFileByUrl() {
        var fileByUrlMsg = OutgoingFileByUrl.builder()
            .chatId("79851150769@c.us")
            .urlFile("https://avatars.mds.yandex.net/get-pdb/477388/77f64197-87d2-42cf-9305-14f49c65f1da/s375")
            .fileName("horse.png")
            .caption("Лошадка")
            .build();

        var response = greenApiClient.sending.sendFileByUrl(fileByUrlMsg);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void sendLocation() {
        var locationMsg = OutgoingLocation.builder()
            .chatId("79851150769@c.us")
            .nameLocation("TEST")
            .address("613123, Perm")
            .latitude(44.9370129)
            .longitude(89.8728409)
            .build();

        var response = greenApiClient.sending.sendLocation(locationMsg);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}