package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TextMessageData extends QuotedMessage {
    private String textMessage;
    private boolean isTemplateMessage;
}
