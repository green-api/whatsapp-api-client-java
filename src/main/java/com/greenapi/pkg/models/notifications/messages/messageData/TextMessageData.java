package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextMessageData {
    private String textMessage;
    private boolean isTemplateMessage;
}
