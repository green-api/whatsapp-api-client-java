package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.MessageReq;
import com.greenapi.client.pkg.models.request.EditMessageReq;
import com.greenapi.client.pkg.models.response.*;
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

        String url = host +
            "/waInstance" + instanceId +
            "/checkWhatsapp/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, Long>();
        requestBody.put("phoneNumber", phoneNumber);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, CheckWhatsAppResp.class);
    }

    /**
     * The method returns a user or a group chat avatar.
     * https://greenapi.com/en/docs/api/service/GetAvatar/
     */
    public ResponseEntity<GetAvatarResp> getAvatar(String chatId) {

        String url = host +
            "/waInstance" + instanceId +
            "/getAvatar/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, GetAvatarResp.class);
    }

    /**
     * The method is aimed for getting a list of the current account contacts.
     * https://greenapi.com/en/docs/api/service/GetContacts/
     */
    public ResponseEntity<List<GetContactsResp>> getContacts() {

        String url = host +
            "/waInstance" + instanceId +
            "/getContacts/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method is aimed for getting information on a contact.
     * https://greenapi.com/en/docs/api/service/GetContactInfo/
     */
    public ResponseEntity<GetContactInfoResp> getContactInfo(String chatId) {

        String url = host +
            "/waInstance" + instanceId +
            "/getContactInfo/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, GetContactInfoResp.class);
    }

    /**
     * The method deletes a message from a chat.
     * https://greenapi.com/en/docs/api/service/deleteMessage/
     */
    public ResponseEntity<String> deleteMessage(DeleteMessageReq deleteMessageReq) {
        String url = host +
            "/waInstance" + instanceId +
            "/deleteMessage/" +
            instanceToken;
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(deleteMessageReq, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }
    // Overloaded method for backward compatibility
    public ResponseEntity<String> deleteMessage(MessageReq messageReq) {
        return deleteMessage(DeleteMessageReq.from(messageReq));
    }

    /**
     * The method edits a message in a chat.
     * https://greenapi.com/en/docs/api/service/editMessage/
     */
    public ResponseEntity<String> editMessage(EditMessageReq messageReq) {
        String url = host +
                "/waInstance" + instanceId +
                "/editMessage/" +
                instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method archives a chat. One can archive chats that have at least one incoming message.
     * https://greenapi.com/en/docs/api/service/archiveChat/
     */
    public ResponseEntity<String> archiveChat(String chatId) {
        var url = new StringBuilder();

        url
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/archiveChat/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method unarchives a chat.
     * https://greenapi.com/en/docs/api/service/unarchiveChat/
     */
    public ResponseEntity<String> unarchiveChat(String chatId) {

        String url = host +
            "/waInstance" + instanceId +
            "/unarchiveChat/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<String, String>();
        requestBody.put("chatId", chatId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * The method is aimed for changing settings of disappearing messages in chats.
     * The standard settings of the application are to be used:
     * 0 (off), 86400 (24 hours), 604800 (7 days), 7776000 (90 days).
     * https://greenapi.com/en/docs/api/service/SetDisappearingChat/
     */
    public ResponseEntity<SetDisappearingChatResp> setDisappearingChat(String chatId, Long ephemeralExpiration) {

        String url = host +
            "/waInstance" + instanceId +
            "/setDisappearingChat/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new HashMap<>();
        requestBody.put("chatId", chatId);
        requestBody.put("ephemeralExpiration", ephemeralExpiration);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SetDisappearingChatResp.class);
    }
}
