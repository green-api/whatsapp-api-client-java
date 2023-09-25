package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtendedTextMessageDataOld {
    private String text;
    private String stanzaId;
    private String participant;
}