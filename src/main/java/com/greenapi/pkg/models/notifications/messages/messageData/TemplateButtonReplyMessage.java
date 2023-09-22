package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TemplateButtonReplyMessage extends QuotedMessage {
    private String selectedIndex;
    private String selectedId;
    private String selectedDisplayText;
    private String stanzaId;
}
