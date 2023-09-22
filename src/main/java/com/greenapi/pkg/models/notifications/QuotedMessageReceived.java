package com.greenapi.pkg.models.notifications;

import com.greenapi.pkg.models.notifications.messages.InstanceData;
import com.greenapi.pkg.models.notifications.messages.QuotedMessageOld;
import com.greenapi.pkg.models.notifications.messages.SenderData;
import com.greenapi.pkg.models.notifications.messages.UrlMessage;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotedMessageReceived<T> implements NotificationBody {
    private String typeWebhook;
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private SenderData senderData;
    private QuotedMessageOld<T> messageData;
}