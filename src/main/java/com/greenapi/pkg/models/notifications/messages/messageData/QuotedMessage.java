package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class QuotedMessage {
    private String stanzaId;
    private String participant;
    private String typeMessage;
}
