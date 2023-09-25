package com.greenapi.pkg.models.notifications;

import com.greenapi.pkg.models.notifications.messages.LocationMessage;
import com.greenapi.pkg.models.notifications.messages.InstanceData;
import com.greenapi.pkg.models.notifications.messages.SenderData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMessageReceived implements NotificationBody {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private SenderData senderData;
    private LocationMessage messageData;
}