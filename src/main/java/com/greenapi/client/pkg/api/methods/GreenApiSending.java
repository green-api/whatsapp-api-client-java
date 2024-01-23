package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.*;
import com.greenapi.client.pkg.models.response.SendFileByUploadResp;
import com.greenapi.client.pkg.models.response.SendMessageResp;
import com.greenapi.client.pkg.models.response.UploadFileResp;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@AllArgsConstructor
@Log4j2
public class GreenApiSending {
    private String host;
    private String hostMedia;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for sending a text message to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendMessage/
     */
    public ResponseEntity<SendMessageResp> sendMessage(OutgoingMessage message) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendMessage/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(message, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a button message to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendButtons/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendButtons(OutgoingButtons buttons) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendButtons/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a message with template list interactive buttons to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendTemplateButtons/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendTemplateButtons(OutgoingTemplateButtons buttons) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendTemplateButtons/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * TThe method is aimed for sending a message with a select button from a list of values to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendListMessage/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendListMessage(OutgoingListMessage dto) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendListMessage/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a contact message.
     * https://greenapi.com/en/docs/api/sending/SendContact/
     */
    public ResponseEntity<SendMessageResp> sendContact(OutgoingContact contact) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendContact/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(contact, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a file uploaded by form (form-data).
     * https://greenapi.com/en/docs/api/sending/SendFileByUpload/
     */
    public ResponseEntity<SendFileByUploadResp> sendFileByUpload(OutgoingFileByUpload dto) {
        String url = hostMedia +
            "/waInstance" + instanceId +
            "/sendFileByUpload/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("chatId", dto.getChatId());
        form.add("file", new FileSystemResource(dto.getFile()));
        form.add("fileName", dto.getFileName());
        form.add("caption", dto.getCaption());
        form.add("quotedMessageId", dto.getQuotedMessageId());

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendFileByUploadResp.class);
    }

    /**
     * The method is aimed for sending a file uploaded by Url.
     * https://greenapi.com/en/docs/api/sending/SendFileByUrl/
     */
    public ResponseEntity<SendMessageResp> sendFileByUrl(OutgoingFileByUrl fileByUrl) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendFileByUrl/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(fileByUrl, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is designed to upload a file to the cloud storage, which can be sent using the sendFileByUrl method.
     * https://greenapi.com/en/docs/api/sending/UploadFile/
     */
    public ResponseEntity<UploadFileResp> uploadFile(File file) throws IOException {

        String url = hostMedia +
            "/waInstance" + instanceId +
            "/uploadFile/" +
            instanceToken;

        var byteArrayResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        var headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType(file.getName())
            .orElse(MediaType.APPLICATION_OCTET_STREAM));

        var requestEntity = new HttpEntity<>(byteArrayResource, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, UploadFileResp.class);
    }

    /**
     * The method is aimed for sending location message
     * https://greenapi.com/en/docs/api/sending/SendLocation/
     */
    public ResponseEntity<SendMessageResp> sendLocation(OutgoingLocation location) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendLocation/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(location, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * This method is intended for sending messages with a poll to a private or group chat.
     * https://greenapi.com/en/docs/api/sending/SendPoll/
     */
    public ResponseEntity<SendMessageResp> sendPoll(OutgoingPoll poll) {

        var url = host +
            "/waInstance" + instanceId +
            "/sendPoll/" + instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(poll, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }
}
