package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.QueueMessage;
import com.greenapi.pkg.models.response.ClearMessagesQueueResp;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class GreenApiQueues {
    private String host;
    private String instanceId;
    private String token;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for getting a list of messages in the queue to be sent.
     * Messages sending rate is managed by Messages sending delay parameter in settings.
     * https://greenapi.com/en/docs/api/queues/ShowMessagesQueue/
     */
    public ResponseEntity<List<QueueMessage>> showMessagesQueue() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/showMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method is aimed for clearing the queue of messages to be sent.
     * https://greenapi.com/en/docs/api/queues/ClearMessagesQueue/
     */
    public ResponseEntity<ClearMessagesQueueResp> clearMessagesQueue() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/clearMessagesQueue/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, ClearMessagesQueueResp.class);
    }
}
