package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import com.greenapi.client.pkg.models.notifications.messages.SenderData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@SuperBuilder
public abstract class MessageWebhook extends NotificationBody {
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private SenderData senderData;
}
