package com.greenapi.client.methods;

import com.greenapi.client.dto.request.MessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Builder
public class GreenApiMarking {
    private String host;
    private String instanceId;
    private String token;

    /**The method is aimed for marking messages in a chat as read.
     * Either all messages or a specified message in a chat can be marked as read.
     * https://green-api.com/en/docs/api/marks/ReadChat/*/
    public ResponseEntity<String> readChat(MessageReq messageReq) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/readChat/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }
}
