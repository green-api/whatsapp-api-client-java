//import com.greenapi.client.pkg.models.request.InstanceSettingsReq;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//
//import java.io.File;
//
//@Log4j2
//class GreenApiAccountTest extends GreenApiTest {
//
//    @Test
//    void getSettings() {
//        var response = greenApi.account.getSettings();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getWaSettings() {
//        var response = greenApi.account.getWaSettings();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void setSetting() {
//        var instanceSettings = InstanceSettingsReq.builder()
//            .delaySendMessagesMilliseconds(15000)
//            .build();
//
//        var response = greenApi.account.setSetting(instanceSettings);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getStateInstance() {
//        var response = greenApi.account.getStateInstance();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getStatusInstance() {
//        var response = greenApi.account.getStatusInstance();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void reboot() {
//        var response = greenApi.account.reboot();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    /*@Test
//    void logout() {
//        var response = greenApiClient.account.logout();
//        log.info();(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }*/
//
//    @Test
//    void getQrCode() {
//        var response = greenApi.account.getQrCode();
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void setProfilePicture() {
//        var file = new File(fileUrl);
//
//        var response = greenApi.account.setProfilePicture(file);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
////    @Test
////    void getAuthorizationCode() {
////        var response = greenApi.account.getAuthorizationCode(79851150769L);
////        log.info(response);
////
////        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
//}