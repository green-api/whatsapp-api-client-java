package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ReactionMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingReactionMessage implements IncomingMessageData {
    private final String typeMessage;
    private final ReactionMessageData extendedTextMessageData;
    private final QuotedMessage quotedMessage;
}
