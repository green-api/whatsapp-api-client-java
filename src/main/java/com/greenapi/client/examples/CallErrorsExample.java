package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.request.CallsDialReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class CallErrorsExample {

    public static void main(String[] args) {
        var greenApi = new GreenApi(
            new RestTemplate(),
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        try {
            greenApi.calls.callsDial(new CallsDialReq("123"));
        } catch (CallException e) {
            log.info("Wrong number: " + e.getCode() + " | " + e.getMessage());
        }

        try {
            greenApi.calls.callsHangUp();
        } catch (CallException e) {
            log.info("Hang up without a call: " + e.getCode() + " | " + e.getMessage());
        }
    }
}
