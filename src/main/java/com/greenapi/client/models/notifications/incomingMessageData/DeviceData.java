package com.greenapi.client.models.notifications.incomingMessageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class DeviceData {
    private final String platform;
    private final String deviceManufacturer;
    private final String deviceModel;
    private final String osVersion;
    private final String waVersion;
    private final Integer battery;
}
