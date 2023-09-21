package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.FileMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMessage {
    private String typeMessage;
    private FileMessageData fileMessageData;
    private QuotedMessage quotedMessage;
}
