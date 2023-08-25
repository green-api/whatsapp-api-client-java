package com.greenapi.client.methods;

import com.greenapi.client.dto.request.MessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Builder
public class GreenApiReceiving {
    private String host;
    private String instanceId;
    private String token;

    public ResponseEntity<String> receiveNotification() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/receiveNotification/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    public ResponseEntity<String> deleteNotification(String receiptId) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/deleteNotification/")
            .append(token)
            .append("/").append(receiptId);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.DELETE, null, String.class);
    }

    public ResponseEntity<String> downloadFile(MessageReq messageReq) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/downloadFile/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }
}
