package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonReplyMessage {
    private String selectedIndex;
    private String selectedId;
    private String selectedDisplayText;
    private String stanzaId;
}
