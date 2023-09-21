package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.LocationMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMessage {
    private String typeMessage;
    private LocationMessageData locationMessageData;
    private QuotedMessage quotedMessage;
}
