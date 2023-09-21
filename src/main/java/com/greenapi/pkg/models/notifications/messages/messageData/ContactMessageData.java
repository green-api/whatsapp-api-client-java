package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessageData {
    private String displayName;
    private String vcard;
    private boolean isForwarded;
    private Integer forwardingScore;
}
