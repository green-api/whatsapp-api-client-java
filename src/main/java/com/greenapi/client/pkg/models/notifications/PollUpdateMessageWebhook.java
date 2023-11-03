package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.PollUpdateMessage;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollUpdateMessageWebhook extends MessageWebhook {
    private PollUpdateMessage messageData;
}
