package com.greenapi.client.methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("queues")
public class GreenApiQueues {
    @Value("${green-api.host}")
    private String host;
    @Value("${green-api.instanceId}")
    private String instanceId;
    @Value("${green-api.token}")
    private String token;
    @Autowired
    private RestTemplate restTemplate;

    /**The method is aimed for getting a list of messages in the queue to be sent.
     * Messages sending rate is managed by Messages sending delay parameter in settings.
     * https://green-api.com/en/docs/api/queues/ShowMessagesQueue/*/
    public ResponseEntity<String> showMessagesQueue() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/showMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for clearing the queue of messages to be sent.
     * https://green-api.com/en/docs/api/queues/ClearMessagesQueue/*/
    public ResponseEntity<String> clearMessagesQueue() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/clearMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }
}
