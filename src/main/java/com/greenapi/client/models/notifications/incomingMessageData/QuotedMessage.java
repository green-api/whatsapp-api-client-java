package com.greenapi.client.models.notifications.incomingMessageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class QuotedMessage {
    private final String stanzaId;
    private final String participant;
    private final String typeMessage;
}
