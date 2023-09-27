package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContactMessageData extends QuotedMessage {
    private String displayName;
    private String vcard;
    private boolean isForwarded;
    private Integer forwardingScore;
}
