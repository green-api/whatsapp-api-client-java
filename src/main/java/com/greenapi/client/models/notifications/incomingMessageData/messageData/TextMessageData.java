package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class TextMessageData {
    private final String textMessage;
    private final boolean isTemplateMessage;
}
