package com.greenapi.client.methods;

import com.greenapi.client.dto.request.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@AllArgsConstructor
@Builder
public class GreenApiSending {
    private String host;
    private String hostMedia;
    private String instanceId;
    private String token;

    /**The method is aimed for sending a text message to a personal or a group chat.
     * https://green-api.com/en/docs/api/sending/SendMessage/*/
    public ResponseEntity<String> sendMessage(OutgoingMessage message) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendMessage/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(message, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending a button message to a personal or a group chat.
     * https://green-api.com/en/docs/api/sending/SendButtons/
     *
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.*/
    public ResponseEntity<String> sendButtons(OutgoingButtons buttons) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendButtons/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending a message with template list interactive buttons to a personal or a group chat.
     * https://green-api.com/en/docs/api/sending/SendTemplateButtons/
     *
     * Attention, please! The method is temporarily not working.
     * When the method is called, a 403 error will be returned.*/
    public ResponseEntity<String> sendTemplateButtons(OutgoingTemplateButtons buttons) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendTemplateButtons/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(buttons, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending a contact message.
     * https://green-api.com/en/docs/api/sending/SendContact/*/
    public ResponseEntity<String> sendContact(OutgoingContact contact) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendContact/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(contact, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending a file uploaded by form (form-data).
     * https://green-api.com/en/docs/api/sending/SendFileByUpload/*/
    public ResponseEntity<String> sendFileByUpload(OutgoingFileByUpload dto) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append(hostMedia)
            .append("/waInstance").append(instanceId)
            .append("/sendFileByUpload/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("chatId", dto.getChatId());
        form.add("file", new FileSystemResource(dto.getFile()));
        form.add("fileName", dto.getFileName());
        form.add("caption", dto.getCaption());
        form.add("quotedMessageId", dto.getQuotedMessageId());

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending a file uploaded by Url.
     * https://green-api.com/en/docs/api/sending/SendFileByUrl/*/
    public ResponseEntity<String> sendFileByUrl(OutgoingFileByUrl fileByUrl) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendFileByUrl/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(fileByUrl, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is designed to upload a file to the cloud storage, which can be sent using the sendFileByUrl method.
     * https://green-api.com/en/docs/api/sending/UploadFile/*/
    public ResponseEntity<String> uploadFile(File file) throws IOException {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(hostMedia)
            .append("/waInstance").append(instanceId)
            .append("/uploadFile/")
            .append(token);

        var byteArrayResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        var headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType(file.getName())
            .orElse(MediaType.APPLICATION_OCTET_STREAM));

        var requestEntity = new HttpEntity<>(byteArrayResource, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for sending location message
     * https://green-api.com/en/docs/api/sending/SendLocation/*/
    public ResponseEntity<String> sendLocation(OutgoingLocation location) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/sendLocation/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(location, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }
}
