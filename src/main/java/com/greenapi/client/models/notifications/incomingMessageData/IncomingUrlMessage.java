package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ExtendedTextMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingUrlMessage implements IncomingMessageData {
    private final String typeMessage;
    private final ExtendedTextMessageData extendedTextMessageData;
    private final QuotedMessage quotedMessage;
}
