package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StateInstanceChanged extends NotificationBody {
    private InstanceData instanceData;
    private Long timestamp;
    private String statusInstance;
}
