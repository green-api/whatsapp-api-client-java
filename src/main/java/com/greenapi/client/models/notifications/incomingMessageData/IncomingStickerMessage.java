package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.StickerMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingStickerMessage implements IncomingMessageData {
    private final String typeMessage;
    private final StickerMessageData fileMessageData;
    private final QuotedMessage quotedMessage;
}
