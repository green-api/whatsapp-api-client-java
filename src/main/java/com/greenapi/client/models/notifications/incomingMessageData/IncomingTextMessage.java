package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.TextMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingTextMessage implements IncomingMessageData {
    private final String typeMessage;
    private final TextMessageData textMessageData;
    private final QuotedMessage quotedMessage;
}
