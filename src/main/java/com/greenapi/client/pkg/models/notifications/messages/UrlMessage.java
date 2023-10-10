package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.ExtendedTextMessageData;
import com.greenapi.client.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMessage {
    private String typeMessage;
    private ExtendedTextMessageData extendedTextMessageData;
    private QuotedMessage quotedMessage;
}
