package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ButtonsResponseMessage {
    private final String selectedButtonId;
    private final String selectedButtonText;
    private final String stanzaId;
}
