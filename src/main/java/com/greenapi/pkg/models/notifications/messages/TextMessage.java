package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import com.greenapi.pkg.models.notifications.messages.messageData.TextMessageData;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextMessage {
    private String typeMessage;
    private TextMessageData textMessageData;
    private QuotedMessage quotedMessage;
}
