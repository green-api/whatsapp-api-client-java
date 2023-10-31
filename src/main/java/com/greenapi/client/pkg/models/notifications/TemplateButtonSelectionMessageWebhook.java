package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.TemplateButtonSelection;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonSelectionMessageWebhook extends MessageWebhook {
    private TemplateButtonSelection messageData;
}
