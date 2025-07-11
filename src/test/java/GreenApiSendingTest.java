//import com.greenapi.client.pkg.models.Contact;
//import com.greenapi.client.pkg.models.Option;
//import com.greenapi.client.pkg.models.request.*;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Log4j2
//class GreenApiSendingTest extends GreenApiTest {
//
//    @Test
//    void sendMessage() {
//        var textMsg = OutgoingMessage.builder()
//            .chatId(chatId)
//            .message("TEST")
//            .build();
//
//        var response = greenApi.sending.sendMessage(textMsg);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void sendContact() {
//        var contactMsg = OutgoingContact.builder()
//            .chatId(chatId)
//            .contact(Contact.builder()
//                .phoneContact(79851111111L)
//                .firstName("TEST")
//                .lastName("TEST")
//                .company("TEST")
//                .build())
//            .build();
//
//        var response = greenApi.sending.sendContact(contactMsg);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void sendFileByUpload() {
//        var file = new File(fileUrl);
//        var fileByUploadMsg = OutgoingFileByUpload.builder()
//            .chatId(chatId)
//            .fileName(file.getName())
//            .file(file)
//            .build();
//
//        var response = greenApi.sending.sendFileByUpload(fileByUploadMsg);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void uploadFile() throws IOException {
//        var file = new File(fileUrl);
//
//        var response = greenApi.sending.uploadFile(file);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void sendFileByUrl() {
//        var fileByUrlMsg = OutgoingFileByUrl.builder()
//            .chatId(chatId)
//            .urlFile("https://avatars.mds.yandex.net/get-pdb/477388/77f64197-87d2-42cf-9305-14f49c65f1da/s375")
//            .fileName("horse.png")
//            .caption("Лошадка")
//            .build();
//
//        var response = greenApi.sending.sendFileByUrl(fileByUrlMsg);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void sendLocation() {
//        var locationMsg = OutgoingLocation.builder()
//            .chatId(chatId)
//            .nameLocation("TEST")
//            .address("613123, Perm")
//            .latitude(44.9370129)
//            .longitude(89.8728409)
//            .build();
//
//        var response = greenApi.sending.sendLocation(locationMsg);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
////    @Test
////    void sendButtons() {
////        var buttons = new ArrayList<Button>();
////        buttons.add(new Button(1, "button1"));
////        buttons.add(new Button(2, "button2"));
////        buttons.add(new Button(3, "button3"));
////
////        var dto = OutgoingButtons.builder()
////            .chatId(chatId)
////            .message("TEST")
////            .footer("FOOTER")
////            .buttons(buttons)
////            .build();
////
////        var response = greenApi.sending.sendButtons(dto);
////        log.info(response);
////
////        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
//
////    @Test
////    void sendTemplateButtons() {
////        var outgoingButtons = new ArrayList<TemplateButtons>();
////        outgoingButtons.add(new TemplateButtons(1,
////            new UrlButton("https://www.google.com/", "test"),
////            new CallButton("call", 79851111111L),
////            new QuickReplyButton(messageId, "reply")));
////
////        var dto = OutgoingTemplateButtons.builder()
////            .chatId(chatId)
////            .message("TEST")
////            .footer("FOOTER")
////            .templateButtons(outgoingButtons)
////            .build();
////
////        var response = greenApi.sending.sendTemplateButtons(dto);
////        log.info(response);
////
////        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
//
////    @Test
////    void sendListMessage() {
////        var rows = new ArrayList<OutgoingListMessage.Row>();
////        rows.add(new OutgoingListMessage.Row("id1", "row1", "description"));
////        rows.add(new OutgoingListMessage.Row("id2", "row2", "description"));
////
////        var sections = new ArrayList<OutgoingListMessage.Section>();
////        sections.add(new OutgoingListMessage.Section("Section1", rows));
////
////        var dto = OutgoingListMessage.builder()
////            .chatId(chatId)
////            .message("message")
////            .title("title")
////            .footer("footer")
////            .buttonText("button text")
////            .sections(sections)
////            .build();
////
////        var response = greenApi.sending.sendListMessage(dto);
////        log.info(response);
////
////        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
//
//    @Test
//    void sendPoll() {
//        var options = new ArrayList<Option>();
//        options.add(new Option("option 1"));
//        options.add(new Option("option 2"));
//        options.add(new Option("option 3"));
//
//        var dto = OutgoingPoll.builder()
//            .chatId(chatId)
//            .message("message")
//            .options(options)
//            .multipleAnswers(false)
//            .build();
//
//        var response = greenApi.sending.sendPoll(dto);
//        log.info(response);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}