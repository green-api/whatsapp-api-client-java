package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ContactMessageData {
    private final String displayName;
    private final String vcard;
    private final boolean isForwarded;
    private final Integer forwardingScore;
}
