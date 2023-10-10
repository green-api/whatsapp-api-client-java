package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.ContactsArrayMessageData;
import com.greenapi.client.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsArrayMessage {
    private String typeMessage;
    private ContactsArrayMessageData messageData;
    private QuotedMessage quotedMessage;
}
