package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ContactMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingContactMessage implements IncomingMessageData {
    private final String typeMessage;
    private final ContactMessageData contactMessageData;
    private final QuotedMessage quotedMessage;
}
