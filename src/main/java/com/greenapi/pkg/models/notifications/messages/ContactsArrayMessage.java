package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ContactsArrayMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsArrayMessage {
    private String typeMessage;
    private ContactsArrayMessageData messageData;
    private QuotedMessage quotedMessage;
}
