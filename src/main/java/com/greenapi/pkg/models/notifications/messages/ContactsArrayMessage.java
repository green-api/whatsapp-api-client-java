package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ContactsArrayMessageData;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsArrayMessage {
    private String typeMessage;
    private ContactsArrayMessageData messageData;
    private QuotedMessage quotedMessage;
}
