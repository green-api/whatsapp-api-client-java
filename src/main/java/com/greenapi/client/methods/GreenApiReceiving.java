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

    /**The method is aimed for receiving one incoming notification from the notifications queue.
     * https://green-api.com/en/docs/api/receiving/technology-http-api/ReceiveNotification/*/
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

    /**The method is aimed for deleting an incoming notification from the notification queue.
     * https://green-api.com/en/docs/api/receiving/technology-http-api/DeleteNotification/*/
    public ResponseEntity<String> deleteNotification(int receiptId) {
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

    /**The method is aimed for downloading incoming and outgoing files.
     * Links to incoming files are transmitted in Incoming messages, and you can also get them using LastIncomingMessages method.
     * You can get links to outgoing files using LastOutgoingMessages method.
     * (Files storage period and, accordingly, the capability to download them is limited by WhatsApp)
     * https://green-api.com/en/docs/api/receiving/files/DownloadFile/*/
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
