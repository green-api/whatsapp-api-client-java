package com.greenapi.client.methods;

import com.greenapi.client.dto.request.MessageReq;
import com.greenapi.client.dto.response.ReadChatResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("marking")
public class GreenApiMarking {
    @Value("${green-api.host}")
    private String host;
    @Value("${green-api.instanceId}")
    private String instanceId;
    @Value("${green-api.token}")
    private String token;
    @Autowired
    @Qualifier("gapiRestTemplate")
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
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, ReadChatResp.class);
    }
}
