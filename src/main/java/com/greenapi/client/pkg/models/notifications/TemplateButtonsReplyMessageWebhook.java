package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.TemplateButtonMessage;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.TemplateButtonReplyMessageData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonsReplyMessageWebhook extends MessageWebhook {
    private TemplateButtonReplyMessageData messageData;
}
