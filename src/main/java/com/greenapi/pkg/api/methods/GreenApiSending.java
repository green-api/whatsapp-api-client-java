package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.*;
import com.greenapi.pkg.models.response.SendFileByUploadResp;
import com.greenapi.pkg.models.response.SendMessageResp;
import com.greenapi.pkg.models.response.UploadFileResp;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@AllArgsConstructor
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
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendMessage/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(message, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a button message to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendButtons/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendButtons(OutgoingButtons buttons) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendButtons/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a message with template list interactive buttons to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendTemplateButtons/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendTemplateButtons(OutgoingTemplateButtons buttons) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendTemplateButtons/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * TThe method is aimed for sending a message with a select button from a list of values to a personal or a group chat.
     * https://greenapi.com/en/docs/api/sending/SendListMessage/
     * <p>
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.
     */
    public ResponseEntity<SendMessageResp> sendListMessage(OutgoingListMessage dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendListMessage/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a contact message.
     * https://greenapi.com/en/docs/api/sending/SendContact/
     */
    public ResponseEntity<SendMessageResp> sendContact(OutgoingContact contact) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendContact/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(contact, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is aimed for sending a file uploaded by form (form-data).
     * https://greenapi.com/en/docs/api/sending/SendFileByUpload/
     */
    public ResponseEntity<SendFileByUploadResp> sendFileByUpload(OutgoingFileByUpload dto) {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append(hostMedia)
            .append("/waInstance").append(instanceId)
            .append("/sendFileByUpload/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("chatId", dto.getChatId());
        form.add("file", new FileSystemResource(dto.getFile()));
        form.add("fileName", dto.getFileName());
        form.add("caption", dto.getCaption());
        form.add("quotedMessageId", dto.getQuotedMessageId());

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendFileByUploadResp.class);
    }

    /**
     * The method is aimed for sending a file uploaded by Url.
     * https://greenapi.com/en/docs/api/sending/SendFileByUrl/
     */
    public ResponseEntity<SendMessageResp> sendFileByUrl(OutgoingFileByUrl fileByUrl) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendFileByUrl/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(fileByUrl, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method is designed to upload a file to the cloud storage, which can be sent using the sendFileByUrl method.
     * https://greenapi.com/en/docs/api/sending/UploadFile/
     */
    public ResponseEntity<UploadFileResp> uploadFile(File file) throws IOException {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(hostMedia)
            .append("/waInstance").append(instanceId)
            .append("/uploadFile/")
            .append(instanceToken);

        var byteArrayResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        var headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType(file.getName())
            .orElse(MediaType.APPLICATION_OCTET_STREAM));

        var requestEntity = new HttpEntity<>(byteArrayResource, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, UploadFileResp.class);
    }

    /**
     * The method is aimed for sending location message
     * https://greenapi.com/en/docs/api/sending/SendLocation/
     */
    public ResponseEntity<SendMessageResp> sendLocation(OutgoingLocation location) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendLocation/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(location, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SendMessageResp.class);
    }
}
