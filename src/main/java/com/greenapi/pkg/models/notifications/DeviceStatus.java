package com.greenapi.pkg.models.notifications;

import com.greenapi.pkg.models.notifications.messages.DeviceData;
import com.greenapi.pkg.models.notifications.messages.InstanceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatus implements NotificationBody {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private DeviceData deviceData;
}
