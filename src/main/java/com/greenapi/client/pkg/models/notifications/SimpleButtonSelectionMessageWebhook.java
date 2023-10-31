package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.SimpleButtonSelectionMessage;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleButtonSelectionMessageWebhook extends MessageWebhook {
    private SimpleButtonSelectionMessage messageData;
}
