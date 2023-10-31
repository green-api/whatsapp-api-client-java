package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.MessageReq;
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

        String url = host +
            "/waInstance" + instanceId +
            "/receiveNotification/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    /**
     * The method is aimed for deleting an incoming notification from the notification queue.
     * https://greenapi.com/en/docs/api/receiving/technology-http-api/DeleteNotification/
     */
    public ResponseEntity<String> deleteNotification(Integer receiptId) {

        String url = host +
            "/waInstance" + instanceId +
            "/deleteNotification/" +
            instanceToken +
            "/" + receiptId;

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    /**
     * The method is aimed for downloading incoming and outgoing files.
     * Links to incoming files are transmitted in Incoming messages, and you can also get them using LastIncomingMessages method.
     * You can get links to outgoing files using LastOutgoingMessages method.
     * (Files storage period and, accordingly, the capability to download them is limited by WhatsApp)
     * https://greenapi.com/en/docs/api/receiving/files/DownloadFile/
     */
    public ResponseEntity<byte[]> downloadFile(MessageReq messageReq) {

        String url = host +
            "/waInstance" + instanceId +
            "/downloadFile/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(messageReq, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);
    }
}
