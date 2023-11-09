package com.greenapi.client.pkg.models.notifications.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.notifications.messages.messageData.TemplateMessageData;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.QuotedMessage;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.TemplateButtonReplyMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonReplyMessage {
    private String typeMessage;
    private TemplateButtonReplyMessageData templateMessage;
}
