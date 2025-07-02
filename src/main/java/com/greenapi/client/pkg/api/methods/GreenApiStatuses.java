package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.response.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.greenapi.client.pkg.models.response.SendMessageResp;
import com.greenapi.client.pkg.models.response.GetStatusStatisticResp;
import com.greenapi.client.pkg.models.request.SendTextStatusResq;
import com.greenapi.client.pkg.models.request.SendVoiceStatusResq;
import com.greenapi.client.pkg.models.request.SendMediaStatusResq;

import java.util.List;

@AllArgsConstructor
public class GreenApiStatuses {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for sending a text status. 
     * https://green-api.com/en/docs/api/statuses/SendTextStatus/
     */
    public ResponseEntity<SendMessageResp> sendTextStatus(SendTextStatusResq request) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendTextStatus/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

     /**
     * The method is aimed for sending a voice status.
     * https://green-api.com/en/docs/api/statuses/SendVoiceStatus/
     */
    public ResponseEntity<SendMessageResp> sendVoiceStatus(SendVoiceStatusResq request) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendVoiceStatus/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

     /**
     * The method is aimed for sending a pictures or video status. 
     * https://green-api.com/en/docs/api/statuses/SendMediaStatus/
     */
    public ResponseEntity<SendMessageResp> sendMediaStatus(SendMediaStatusResq request) {

        String url = host +
            "/waInstance" + instanceId +
            "/sendMediaStatus/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendMessageResp.class);
    }

    /**
     * The method returns the incoming status messages of the instance.
     * In the default mode the incoming status messages for 24 hours are returned.
     * https://green-api.com/en/docs/api/statuses/GetIncomingStatuses/
     */
    public ResponseEntity<List<ChatMessage>> getIncomingStatuses() {
        return getIncomingStatuses(null);
    }

    public ResponseEntity<List<ChatMessage>> getIncomingStatuses(Integer minutes) {
        String url = host +
                "/waInstance" + instanceId +
                "/getIncomingStatuses/" +
                instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = minutes != null
                ? new HttpEntity<>(minutes, headers)
                : new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * The method returns the outgoing statuses of the account.
     * In the default mode the outgoing status messages for 24 hours are returned.
     * https://green-api.com/en/docs/api/statuses/GetOutgoingStatuses/
     */
    public ResponseEntity<List<ChatMessage>> getOutgoingStatuses() {
        return getOutgoingStatuses(null);
    }

    public ResponseEntity<List<ChatMessage>> getOutgoingStatuses(Integer minutes) {

        String url = host +
            "/waInstance" + instanceId +
            "/getOutgoingStatuses/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = minutes != null
                ? new HttpEntity<>(minutes, headers)
                : new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
    }

    /**
     * The method returns an array of recipients marked sent/delivered/read for a given status
     * https://green-api.com/en/docs/api/statuses/GetStatusStatistic/
     */
    public ResponseEntity<List<GetStatusStatisticResp>> getStatusStatistic(String idMessage) {
        
        String url = host +
            "/waInstance" + instanceId +
            "/getStatusStatistic/" +
            instanceToken + "?idMessage=" + idMessage;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
    }
}
