package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.TemplateMessageData;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonMessage {
    private String typeMessage;
    private TemplateMessageData templateMessage;
    private QuotedMessage quotedMessage;
}
