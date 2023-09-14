package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ListResponseMessage {
    private final String selectedIndex;
    private final String selectedId;
    private final String selectedDisplayText;
    private final String stanzaId;
}
