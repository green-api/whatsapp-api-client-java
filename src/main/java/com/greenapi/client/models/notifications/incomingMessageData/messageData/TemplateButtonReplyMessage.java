package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import com.greenapi.client.models.notifications.incomingMessageData.IncomingMessageData;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@Builder
public class TemplateButtonReplyMessage {
    private final String selectedIndex;
    private final String selectedId;
    private final String selectedDisplayText;
    private final String stanzaId;
}
