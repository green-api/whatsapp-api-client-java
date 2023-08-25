package com.greenapi.client;

import com.greenapi.client.methods.GreenApiAccount;
import com.greenapi.client.methods.GreenApiGroups;
import com.greenapi.client.methods.GreenApiJournals;
import com.greenapi.client.methods.GreenApiSending;
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

        return greenApiClient;
    }
}
