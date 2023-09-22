package com.greenapi.pkg.models.notifications.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceData {
    private String platform;
    private String deviceManufacturer;
    private String deviceModel;
    private String osVersion;
    private String waVersion;
    private Integer battery;
}
