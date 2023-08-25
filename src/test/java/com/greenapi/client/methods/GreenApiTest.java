package com.greenapi.client.methods;

import com.greenapi.client.GreenApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class GreenApiTest {
    @Autowired
    protected GreenApiClient greenApiClient;

    /*protected GreenApiClient greenApiClient = new GreenApiClient(
        "https://api.green-api.com",
        "https://media.green-api.com",
        "1101848922",
        "651450d7045842a58ca7bb62a1eb6e4b09426645ae574fdfaf");*/
}
