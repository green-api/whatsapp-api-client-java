package com.greenapi.pkg.models.notifications.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotedMessage {
    private String stanzaId;
    private String participant;
    private String typeMessage;
    private String textMessage;
}
