package com.greenapi.client.methods;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class GreenApiWebHooks {
    private String host;
    private String instanceId;
    private String token;
}
