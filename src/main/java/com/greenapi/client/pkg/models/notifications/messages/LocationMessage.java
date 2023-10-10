package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.LocationMessageData;
import com.greenapi.client.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMessage {
    private String typeMessage;
    private LocationMessageData locationMessageData;
    private QuotedMessage quotedMessage;
}
