package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.MessageReq;
import com.greenapi.pkg.models.response.*;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class GreenApiService {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method checks WhatsApp account availability on a phone number.
     * https://greenapi.com/en/docs/api/service/CheckWhatsapp/
     */
    public ResponseEntity<CheckWhatsAppResp> checkWhatsapp(Long phoneNumber) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/checkWhatsapp/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, Long>();
        requestBody.put("phoneNumber", phoneNumber);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, CheckWhatsAppResp.class);
    }

    /**
     * The method returns a user or a group chat avatar.
     * https://greenapi.com/en/docs/api/service/GetAvatar/
     */
    public ResponseEntity<GetAvatarResp> getAvatar(String chatId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getAvatar/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, GetAvatarResp.class);
    }

    /**
     * The method is aimed for getting a list of the current account contacts.
     * https://greenapi.com/en/docs/api/service/GetContacts/
     */
    public ResponseEntity<List<GetContactsResp>> getContacts() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getContacts/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method is aimed for getting information on a contact.
     * https://greenapi.com/en/docs/api/service/GetContactInfo/
     */
    public ResponseEntity<GetContactInfoResp> getContactInfo(String chatId) { //TODO 400 Bad Request: "{"statusCode":400,"timestamp":"2023-09-13T22:29:37.459Z","path":"/waInstance1101848922/getContactInfo/651450d7045842a58ca7bb62a1eb6e4b09426645ae574fdfaf","message":"Validation failed. Details: 'chatId' is required"}"
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getContactInfo/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, GetContactInfoResp.class);
    }

    /**
     * The method deletes a message from a chat.
     * https://greenapi.com/en/docs/api/service/deleteMessage/
     */
    public ResponseEntity<String> deleteMessage(MessageReq messageReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/deleteMessage/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method archives a chat. One can archive chats that have at least one incoming message.
     * https://greenapi.com/en/docs/api/service/archiveChat/
     */
    public ResponseEntity<String> archiveChat(String chatId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/archiveChat/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method unarchives a chat.
     * https://greenapi.com/en/docs/api/service/unarchiveChat/
     */
    public ResponseEntity<String> unarchiveChat(String chatId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/unarchiveChat/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method is aimed for changing settings of disappearing messages in chats.
     * The standard settings of the application are to be used:
     * 0 (off), 86400 (24 hours), 604800 (7 days), 7776000 (90 days).
     * https://greenapi.com/en/docs/api/service/SetDisappearingChat/
     */
    public ResponseEntity<SetDisappearingChatResp> setDisappearingChat(String chatId, Long ephemeralExpiration) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setDisappearingChat/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<>();
        requestBody.put("chatId", chatId);
        requestBody.put("ephemeralExpiration", ephemeralExpiration);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SetDisappearingChatResp.class);
    }
}
