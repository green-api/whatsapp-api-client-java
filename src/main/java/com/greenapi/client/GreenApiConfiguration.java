package com.greenapi.client;

import com.greenapi.client.methods.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreenApiConfiguration {
    @Value("${green-api.test-data.host}")
    private String host;
    @Value("${green-api.test-data.hostMedia}")
    private String hostMedia;
    @Value("${green-api.test-data.instanceId}")
    private String instanceId;
    @Value("${green-api.test-data.token}")
    private String token;

    @Bean
    public GreenApiClient greenApiClient() {
        var greenApiClient = new GreenApiClient();
        greenApiClient.account = new GreenApiAccount(host, instanceId, token);
        greenApiClient.sending = new GreenApiSending(host, hostMedia, instanceId, token);
        greenApiClient.journals = new GreenApiJournals(host, instanceId, token);
        greenApiClient.groups = new GreenApiGroups(host, instanceId, token);
        greenApiClient.marking = new GreenApiMarking(host, instanceId, token);
        greenApiClient.queues = new GreenApiQueues(host, instanceId, token);
        greenApiClient.webHooks = new GreenApiWebHooks(host, instanceId, token);
        greenApiClient.service = new GreenApiService(host, instanceId, token);
        greenApiClient.receiving = new GreenApiReceiving(host, instanceId, token);

        return greenApiClient;
    }
}
