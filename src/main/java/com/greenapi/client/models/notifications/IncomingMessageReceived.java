package com.greenapi.client.models.notifications;

import com.greenapi.client.models.notifications.incomingMessageData.IncomingMessageData;
import com.greenapi.client.models.notifications.incomingMessageData.InstanceData;
import com.greenapi.client.models.notifications.incomingMessageData.SenderData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomingMessageReceived {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private SenderData senderData;
    private IncomingMessageData messageData;
}
