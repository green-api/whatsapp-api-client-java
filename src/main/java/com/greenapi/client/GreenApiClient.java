package com.greenapi.client;

import com.greenapi.client.methods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreenApiClient {
    @Autowired
    public GreenApiAccount account;
    @Autowired
    public GreenApiSending sending;
    @Autowired
    public GreenApiJournals journals;
    @Autowired
    public GreenApiGroups groups;
    @Autowired
    public GreenApiQueues queues;
    @Autowired
    public GreenApiMarking marking;
    @Autowired
    public GreenApiReceiving receiving;
    @Autowired
    public GreenApiService service;
}
