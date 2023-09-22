package com.greenapi.pkg.models.notifications;

import com.greenapi.pkg.models.notifications.messages.AvatarInfo;
import com.greenapi.pkg.models.notifications.messages.InstanceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactAvatar implements NotificationBody {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private AvatarInfo avatarInfo;
}
