package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.ExtendedTextMessageDataOld;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotedMessageOld<T> {
    private String typeMessage;
    private ExtendedTextMessageDataOld extendedTextMessageData;
    private T quotedMessage;
}
