package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.FileMessageData;
import com.greenapi.client.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMessage {
    private String typeMessage;
    private FileMessageData fileMessageData;
    private QuotedMessage quotedMessage;
}
