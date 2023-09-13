package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.FileMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingFileMessage implements IncomingMessageData {
    private final String typeMessage;
    private final FileMessageData fileMessageData;
    private final QuotedMessage quotedMessage;
}
