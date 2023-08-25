package com.greenapi.client.methods;

import com.greenapi.client.GreenApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class GreenApiTest {
    @Autowired
    protected GreenApiClient greenApiClient;
}
