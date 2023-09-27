package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.MessageReq;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class GreenApiReceiving {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for receiving one incoming notification from the notifications queue.
     * https://greenapi.com/en/docs/api/receiving/technology-http-api/ReceiveNotification/
     */
    public ResponseEntity<String> receiveNotification() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/receiveNotification/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**
     * The method is aimed for deleting an incoming notification from the notification queue.
     * https://greenapi.com/en/docs/api/receiving/technology-http-api/DeleteNotification/
     */
    public ResponseEntity<String> deleteNotification(int receiptId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/deleteNotification/")
            .append(instanceToken)
            .append("/").append(receiptId);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.DELETE, null, String.class);
    }

    /**
     * The method is aimed for downloading incoming and outgoing files.
     * Links to incoming files are transmitted in Incoming messages, and you can also get them using LastIncomingMessages method.
     * You can get links to outgoing files using LastOutgoingMessages method.
     * (Files storage period and, accordingly, the capability to download them is limited by WhatsApp)
     * https://greenapi.com/en/docs/api/receiving/files/DownloadFile/
     */
    public ResponseEntity<byte[]> downloadFile(MessageReq messageReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/downloadFile/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, byte[].class);
    }
}
