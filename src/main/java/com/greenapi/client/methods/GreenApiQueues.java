package com.greenapi.client.methods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Builder
public class GreenApiQueues {
    private String host;
    private String instanceId;
    private String token;

    public ResponseEntity<String> showMessagesQueue() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/showMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    public ResponseEntity<String> clearMessagesQueue() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/clearMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }
}
