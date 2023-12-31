package com.greenapi.client.pkg.models.notifications.messages.quotedMessageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateButtonReplyMessageData extends QuotedMessage {
    private String selectedIndex;
    private String selectedId;
    private String selectedDisplayText;
    private String stanzaId;
}
