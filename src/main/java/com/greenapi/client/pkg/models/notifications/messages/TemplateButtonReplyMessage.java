package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.TemplateButtonReplyMessageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonReplyMessage {
    private String typeMessage;
    private TemplateButtonReplyMessageData templateMessage;
}
