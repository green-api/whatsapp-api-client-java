package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.QuotedMessage;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateButtonReplyMessageData {
    private String selectedIndex;
    private String selectedId;
    private String selectedDisplayText;
    private String stanzaId;
}
