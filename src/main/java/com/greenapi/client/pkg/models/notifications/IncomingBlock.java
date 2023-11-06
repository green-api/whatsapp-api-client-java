package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingBlock extends NotificationBody {
    private InstanceData instanceData;
    private Long timestamp;
    private String chatId;
    private String chatState;
}
