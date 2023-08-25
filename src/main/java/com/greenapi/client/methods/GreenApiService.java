package com.greenapi.client.methods;

import com.greenapi.client.dto.request.MessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@AllArgsConstructor
@Builder
public class GreenApiService {
    private String host;
    private String instanceId;
    private String token;

    public ResponseEntity<String> checkWhatsapp(Long phoneNumber) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/checkWhatsapp/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, Long>();
        requestBody.put("phoneNumber", phoneNumber);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> getAvatar(String chatId) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getAvatar/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> getContacts() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getContacts/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    public ResponseEntity<String> getContactInfo(String chatId) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getContactInfo/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> deleteMessage(MessageReq messageReq) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/deleteMessage/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> archiveChat(String chatId) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/archiveChat/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> unarchiveChat(String chatId) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/unarchiveChat/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> setDisappearingChat(String chatId, Long ephemeralExpiration) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setDisappearingChat/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<>();
        requestBody.put("chatId", chatId);
        requestBody.put("ephemeralExpiration", ephemeralExpiration);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }


}
