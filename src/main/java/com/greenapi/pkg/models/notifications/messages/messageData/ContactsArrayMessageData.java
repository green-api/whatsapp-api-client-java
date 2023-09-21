package com.greenapi.pkg.models.notifications.messages.messageData;

import com.greenapi.pkg.models.ChatContact;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsArrayMessageData {
    private List<ChatContact> contacts;
    private boolean isForwarded;
    private Integer forwardingScore;
}
