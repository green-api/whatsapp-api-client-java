package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ContactsArrayMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingContactsArrayMessage implements IncomingMessageData {
    private final String typeMessage;
    private final ContactsArrayMessageData messageData;
    private final QuotedMessage quotedMessage;
}
