package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ExtendedTextMessageData;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMessage {
    private String typeMessage;
    private ExtendedTextMessageData extendedTextMessageData;
    private QuotedMessage quotedMessage;
}
