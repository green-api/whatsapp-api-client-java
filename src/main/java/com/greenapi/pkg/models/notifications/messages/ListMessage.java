package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ListMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListMessage {
    private String typeMessage;
    private ListMessageData listMessage;
    private QuotedMessage quotedMessage;
}
