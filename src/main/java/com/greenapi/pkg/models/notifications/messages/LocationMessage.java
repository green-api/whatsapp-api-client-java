package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.LocationMessageData;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMessage {
    private String typeMessage;
    private LocationMessageData locationMessageData;
    private QuotedMessage quotedMessage;
}
