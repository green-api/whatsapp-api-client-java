package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ExtendedTextMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMessage {
    private String typeMessage;
    private ExtendedTextMessageData extendedTextMessageData;
    private QuotedMessage quotedMessage;
}
