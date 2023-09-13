package com.greenapi.client;

import com.greenapi.client.methods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreenApiClient {
    public GreenApiAccount account;
    public GreenApiSending sending;
    public GreenApiJournals journals;
    public GreenApiGroups groups;
    public GreenApiQueues queues;
    public GreenApiMarking marking;
    public GreenApiReceiving receiving;
    public GreenApiService service;

    @Autowired
    public GreenApiClient(
        GreenApiAccount account,
        GreenApiSending sending,
        GreenApiJournals journals,
        GreenApiGroups groups,
        GreenApiQueues queues,
        GreenApiMarking marking,
        GreenApiReceiving receiving,
        GreenApiService service
    ) {
        this.account = account;
        this.sending = sending;
        this.journals = journals;
        this.groups = groups;
        this.queues = queues;
        this.marking = marking;
        this.receiving = receiving;
        this.service = service;
    }
}
