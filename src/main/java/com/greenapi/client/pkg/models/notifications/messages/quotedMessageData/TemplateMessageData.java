package com.greenapi.client.pkg.models.notifications.messages.quotedMessageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.TemplateButtons;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateMessageData extends QuotedMessage{
    private String namespace;
    private String elementName;
    private String contentText;
    private String footer;
    private List<TemplateButtons> buttons;
    private boolean isForwarded;
    private Long forwardingScore;
}
