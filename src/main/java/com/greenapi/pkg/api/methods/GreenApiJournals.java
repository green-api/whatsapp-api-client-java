package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.GetChatHistoryReq;
import com.greenapi.pkg.models.request.MessageReq;
import com.greenapi.pkg.models.response.ChatMessage;
import com.greenapi.pkg.models.ChatHistoryMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class GreenApiJournals {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method returns the chat message history.
     * https://greenapi.com/en/docs/api/journals/GetChatHistory/
     */
    public ResponseEntity<List<ChatHistoryMessage>> getChatHistory(GetChatHistoryReq getChatHistoryReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getChatHistory/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(getChatHistoryReq, headers);
        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method returns the chat message.
     * https://greenapi.com/en/docs/api/journals/GetMessage/
     */
    public ResponseEntity<ChatMessage> getMessage(MessageReq messageReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getMessage/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, ChatMessage.class);
    }

    /**
     * The method returns the last incoming messages of the account.
     * In the default mode the incoming messages for 24 hours are returned.
     * https://greenapi.com/en/docs/api/journals/LastIncomingMessages/
     */
    public ResponseEntity<List<ChatMessage>> lastIncomingMessages(Integer minutes) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/lastIncomingMessages/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(minutes, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method returns the last outgoing messages of the account.
     * In the default mode the last messages for 24 hours are returned.
     * https://greenapi.com/en/docs/api/journals/LastOutgoingMessages/
     */
    public ResponseEntity<List<ChatMessage>> lastOutgoingMessages(Integer minutes) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/lastOutgoingMessages/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(minutes, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
        });
    }
}
