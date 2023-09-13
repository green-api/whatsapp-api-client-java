package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.LocationMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingLocationMessage implements IncomingMessageData {
    private final String typeMessage;
    private final LocationMessageData locationMessageData;
    private final QuotedMessage quotedMessage;
}
