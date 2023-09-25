package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.MessageReq;
import com.greenapi.pkg.models.response.ReadChatResp;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class GreenApiMarking {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for marking messages in a chat as read.
     * Either all messages or a specified message in a chat can be marked as read.
     * https://greenapi.com/en/docs/api/marks/ReadChat/
     */
    public ResponseEntity<ReadChatResp> readChat(MessageReq messageReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/readChat/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, ReadChatResp.class);
    }
}