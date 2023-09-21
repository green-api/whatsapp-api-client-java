package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ReactionMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionMessage {
    private String typeMessage;
    private ReactionMessageData extendedTextMessageData;
    private QuotedMessage quotedMessage;
}
