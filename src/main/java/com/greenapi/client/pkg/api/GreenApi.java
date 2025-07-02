package com.greenapi.client.pkg.api;

import com.greenapi.client.pkg.api.methods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GreenApi {
    public GreenApiAccount account;
    public GreenApiSending sending;
    public GreenApiJournals journals;
    public GreenApiGroups groups;
    public GreenApiQueues queues;
    public GreenApiMarking marking;
    public GreenApiReceiving receiving;
    public GreenApiService service;
    public GreenApiStatuses statuses;

    @Autowired
    public GreenApi(RestTemplate restTemplate,
                    @Value("${green-api.hostMedia}") String hostMedia,
                    @Value("${green-api.host}") String host,
                    @Value("${green-api.instanceId}") String instanceId,
                    @Value("${green-api.token}") String instanceToken) {
        this.account = new GreenApiAccount(host, instanceId, instanceToken, restTemplate);
        this.sending = new GreenApiSending(host, hostMedia, instanceId, instanceToken, restTemplate);
        this.journals = new GreenApiJournals(host, instanceId, instanceToken, restTemplate);
        this.groups = new GreenApiGroups(host, instanceId, instanceToken, restTemplate);
        this.queues = new GreenApiQueues(host, instanceId, instanceToken, restTemplate);
        this.marking = new GreenApiMarking(host, instanceId, instanceToken, restTemplate);
        this.receiving = new GreenApiReceiving(host, instanceId, instanceToken, restTemplate);
        this.service = new GreenApiService(host, instanceId, instanceToken, restTemplate);
        this.statuses = new GreenApiStatuses(host, instanceId, instanceToken, restTemplate);
    }
}
