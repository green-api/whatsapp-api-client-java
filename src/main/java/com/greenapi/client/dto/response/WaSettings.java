package com.greenapi.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaSettings {
    private String avatar;
    private String phone;
    private String stateInstance;
    private String deviceId;
}
