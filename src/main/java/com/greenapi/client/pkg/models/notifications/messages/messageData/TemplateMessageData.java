package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.Button;
import com.greenapi.client.pkg.models.TemplateButtons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateMessageData {
    private String namespace;
    private String elementName;
    private String contentText;
    private String footer;
    private List<TemplateButtons> buttons;
    private boolean isForwarded;
    private Long forwardingScore;
}
