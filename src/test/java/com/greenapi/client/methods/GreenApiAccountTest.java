package com.greenapi.client.methods;

import com.greenapi.client.dto.request.InstanceSettings;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;

@Log4j2
class GreenApiAccountTest extends GreenApiTest {

    @Test
    void getSettings() {
        var response = greenApiClient.account.getSettings();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setSetting() {
        var instanceSettings = InstanceSettings.builder()
            .delaySendMessagesMilliseconds(15000)
            .build();

        var response = greenApiClient.account.setSetting(instanceSettings);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStateInstance() {
        var response = greenApiClient.account.getStateInstance();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStatusInstance() {
        var response = greenApiClient.account.getStatusInstance();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void reboot() {
        var response = greenApiClient.account.reboot();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*@Test
    void logout() {
        var response = greenApiClient.account.logout();
        log.info();(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    void getQrCode() {
        var response = greenApiClient.account.getQrCode();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setProfilePicture() {
        var file = new File(fileUrl);

        var response = greenApiClient.account.setProfilePicture(file);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}