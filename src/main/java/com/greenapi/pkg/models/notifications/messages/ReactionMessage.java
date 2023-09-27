package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.QuotedDataReaction;
import com.greenapi.pkg.models.notifications.messages.messageData.ReactionMessageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionMessage {
    private String typeMessage;
    private ReactionMessageData extendedTextMessageData;
    private QuotedDataReaction quotedMessage;
}
