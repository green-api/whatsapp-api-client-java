package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.messageData.GroupInviteMessageData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInviteMessageWebhook extends MessageWebhook {
    private GroupInviteMessageData messageData;
}
