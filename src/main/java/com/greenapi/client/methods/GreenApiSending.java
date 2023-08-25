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

        System.out.println(requestEntity);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

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
