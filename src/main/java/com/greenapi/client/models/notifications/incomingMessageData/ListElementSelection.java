package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ListResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class ListElementSelection implements IncomingMessageData {
    private final String typeMessage;
    private final ListResponseMessage listResponseMessage;
    private final QuotedMessage quotedMessage;
}
