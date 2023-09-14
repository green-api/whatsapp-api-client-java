package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import com.greenapi.client.models.notifications.incomingMessageData.IncomingMessageData;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@Builder
public class ButtonsResponseMessage {
    private final String selectedButtonId;
    private final String selectedButtonText;
    private final String stanzaId;
}
