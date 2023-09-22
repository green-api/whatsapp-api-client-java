package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import com.greenapi.pkg.models.notifications.messages.messageData.StickerMessageData;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StickerMessage {
    private String typeMessage;
    private StickerMessageData fileMessageData;
    private QuotedMessage quotedMessage;
}
