package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Log4j2
public class AccountMethodsExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new AccountMethodsExample();
        example.getStateInstanceHistoryExample(greenApi);
        example.updateApiTokenExample(greenApi);
    }

    /**
     * Returns the last 50 authorization state changes of the instance.
     */
    private void getStateInstanceHistoryExample(GreenApi greenApi) {
        var response = greenApi.account.getStateInstanceHistory(50);

        if (response.getStatusCode().is2xxSuccessful()) {
            var history = Objects.requireNonNull(response.getBody());
            log.info("State history records: " + history.size());
            history.forEach(record -> log.info(
                "State: " + record.getStateInstance() +
                " | phone: " + record.getPhoneNumber() +
                " | at: " + record.getTimestamp()));
        } else {
            log.warn("Failed to get state history, status: " + response.getStatusCode());
        }
    }

    /**
     * Generates a new API token, invalidating the current one.
     * Beta feature. After calling this method the current token becomes invalid.
     */
    private void updateApiTokenExample(GreenApi greenApi) {
        var response = greenApi.account.updateApiToken();

        if (response.getStatusCode().is2xxSuccessful()) {
            var newToken = Objects.requireNonNull(response.getBody()).getApiTokenInstance();
            log.info("New API token: " + newToken);
        } else {
            log.warn("Failed to update token, status: " + response.getStatusCode());
        }
    }
}
