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

    /**The method checks WhatsApp account availability on a phone number.
     * https://green-api.com/en/docs/api/service/CheckWhatsapp/*/
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

    /**The method returns a user or a group chat avatar.
     * https://green-api.com/en/docs/api/service/GetAvatar/*/
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

    /**The method is aimed for getting a list of the current account contacts.
     * https://green-api.com/en/docs/api/service/GetContacts/*/
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

    /**The method is aimed for getting information on a contact.
     * https://green-api.com/en/docs/api/service/GetContactInfo/*/
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

    /**The method deletes a message from a chat.
     * https://green-api.com/en/docs/api/service/deleteMessage/*/
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

    /**The method archives a chat. One can archive chats that have at least one incoming message.
     * https://green-api.com/en/docs/api/service/archiveChat/*/
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

    /**The method unarchives a chat.
     * https://green-api.com/en/docs/api/service/unarchiveChat/*/
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

    /**The method is aimed for changing settings of disappearing messages in chats.
     * The standard settings of the application are to be used:
     * 0 (off), 86400 (24 hours), 604800 (7 days), 7776000 (90 days).
     * https://green-api.com/en/docs/api/service/SetDisappearingChat/*/
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
