package com.greenapi.client.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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