package com.greenapi.client.models.notifications;

import com.greenapi.client.models.notifications.incomingMessageData.AvatarInfo;
import com.greenapi.client.models.notifications.incomingMessageData.InstanceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactAvatar {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private AvatarInfo avatarInfo;
}
