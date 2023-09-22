package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.ContactMessageData;
import com.greenapi.pkg.models.notifications.messages.messageData.QuotedMessage;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage {
    private String typeMessage;
    private ContactMessageData contactMessageData;
    private QuotedMessage quotedMessage;
}
