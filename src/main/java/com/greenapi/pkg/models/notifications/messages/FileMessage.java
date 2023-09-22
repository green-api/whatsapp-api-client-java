package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.FileMessageData;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMessage {
    private String typeMessage;
    private FileMessageData fileMessageData;
    private QuotedMessage quotedMessage;
}
