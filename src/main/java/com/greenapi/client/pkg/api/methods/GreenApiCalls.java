package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.api.calls.CallErrors;
import com.greenapi.client.pkg.api.calls.CallsConnection;
import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.calls.CallState;
import com.greenapi.client.pkg.models.calls.CallsConnectionOptions;
import com.greenapi.client.pkg.models.request.CallsDialReq;
import com.greenapi.client.pkg.models.request.GetCallRecordingReq;
import com.greenapi.client.pkg.models.response.CallIdResp;
import com.greenapi.client.pkg.models.response.CallRecordingResp;
import com.greenapi.client.pkg.models.response.IceServer;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.CALLS_RTC_ENDPOINT;

/**
 * WhatsApp voice calls: control methods and the live calls connection with the call audio.
 * The methods throw CallException with a readable message and a code.
 * https://green-api.com/en/docs/calls/
 */
@AllArgsConstructor
public class GreenApiCalls {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * Starts an outgoing voice call. Outgoing calls work only to chats that wrote or called in the last 72 hours.
     * The call ID is the same as idMessage in the call webhooks.
     * https://green-api.com/en/docs/api/calls/callsDial/
     */
    public ResponseEntity<CallIdResp> callsDial(CallsDialReq callsDialReq) {
        return exchange("callsDial", HttpMethod.POST, callsDialReq, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Accepts the incoming call.
     * https://green-api.com/en/docs/api/calls/callsAccept/
     */
    public ResponseEntity<CallIdResp> callsAccept() {
        return exchange("callsAccept", HttpMethod.POST, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Rejects the incoming call.
     * https://green-api.com/en/docs/api/calls/callsReject/
     */
    public ResponseEntity<CallIdResp> callsReject() {
        return exchange("callsReject", HttpMethod.POST, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Ends the current call.
     * https://green-api.com/en/docs/api/calls/callsHangUp/
     */
    public ResponseEntity<CallIdResp> callsHangUp() {
        return exchange("callsHangUp", HttpMethod.POST, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Returns the current call state: idle, inc-call, out-call or on-call.
     * https://green-api.com/en/docs/api/calls/callsState/
     */
    public ResponseEntity<CallState> callsState() {
        return exchange("callsState", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Returns the ICE servers for the call audio. The calls connection uses it itself.
     * https://green-api.com/en/docs/api/calls/callsGetIceServers/
     */
    public ResponseEntity<List<IceServer>> callsGetIceServers() {
        return exchange("callsGetIceServers", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Returns the recording of a call. downloadUrl works for 1 hour.
     */
    public ResponseEntity<CallRecordingResp> getCallRecording(GetCallRecordingReq getCallRecordingReq) {
        return exchange("getCallRecording", HttpMethod.POST, getCallRecordingReq, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Creates the live calls connection: call state, incoming calls, end reasons and the call audio. Call open() on it.
     * The audio needs the optional dependency dev.onvoid.webrtc:webrtc-java.
     */
    public CallsConnection connectCalls() {
        return connectCalls(CallsConnectionOptions.builder().build());
    }

    /**
     * Creates the live calls connection with options, for example the PCM sample rate of realtime audio.
     */
    public CallsConnection connectCalls(CallsConnectionOptions options) {
        return new CallsConnection(this, getCallsRtcUrl(), options);
    }

    /**
     * Returns the WebSocket URL of the calls connection. The URL contains the instance token.
     */
    public String getCallsRtcUrl() {
        return host.replaceFirst("^http", "ws") +
            "/waInstance" + instanceId +
            "/" + CALLS_RTC_ENDPOINT + "/" +
            instanceToken;
    }

    private <T> ResponseEntity<T> exchange(String method, HttpMethod httpMethod, Object body, ParameterizedTypeReference<T> type) {
        String url = host +
            "/waInstance" + instanceId +
            "/" + method + "/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var requestEntity = body == null ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(url, httpMethod, requestEntity, type);
        } catch (RestClientResponseException e) {
            throw CallErrors.fromHttp(e, method);
        } catch (ResourceAccessException e) {
            throw new CallException(CallErrorCode.CONNECTION_FAILED, CallErrorCode.CONNECTION_FAILED.getMessage(), null, e.getMessage(), e);
        }
    }
}
