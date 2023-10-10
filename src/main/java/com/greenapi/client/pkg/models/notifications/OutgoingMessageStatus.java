package com.greenapi.client.pkg.models.notifications;

import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutgoingMessageStatus implements NotificationBody {
    private String typeWebhook;
    private String chatId;
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private String status;
    private Boolean sendByApi;
}
