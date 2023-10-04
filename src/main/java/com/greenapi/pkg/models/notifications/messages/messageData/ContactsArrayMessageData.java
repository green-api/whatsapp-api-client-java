package com.greenapi.pkg.models.notifications.messages.messageData;

import com.greenapi.pkg.models.ChatContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContactsArrayMessageData extends QuotedMessage {
    private List<ChatContact> contacts;
    private Boolean isForwarded;
    private Integer forwardingScore;
}
