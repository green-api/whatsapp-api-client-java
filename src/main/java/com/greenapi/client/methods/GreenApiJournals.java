package com.greenapi.client.methods;

import com.greenapi.client.dto.request.GetChatHistoryReq;
import com.greenapi.client.dto.request.MessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Builder
public class GreenApiJournals {
    private String host;
    private String instanceId;
    private String token;

    public ResponseEntity<String> getChatHistory(GetChatHistoryReq getChatHistoryReq) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getChatHistory/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(getChatHistoryReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> getMessage(MessageReq messageReq) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getMessage/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<String> lastIncomingMessages(Integer minutes) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/lastIncomingMessages/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(minutes, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, requestEntity, String.class);
    }

    public ResponseEntity<String> lastOutgoingMessages(Integer minutes) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/lastOutgoingMessages/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(minutes, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, requestEntity, String.class);
    }
}
