package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class LocationMessageData {
    private final Double latitude;
    private final Double longitude;
    private final String jpegThumbnail;
    private final boolean isForwarded;
    private final Integer forwardingScore;
}
