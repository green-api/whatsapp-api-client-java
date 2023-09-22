package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReactionMessageData extends QuotedMessage {
    private String text;
}