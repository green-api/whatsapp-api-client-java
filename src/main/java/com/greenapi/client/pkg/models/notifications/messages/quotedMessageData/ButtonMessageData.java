package com.greenapi.client.pkg.models.notifications.messages.quotedMessageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ButtonMessageData extends QuotedMessage {
    private String message;
    private String footer;
    private List<Button> buttons;
    private String chatId;
    private String quotedMessageId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class Button {
        private Integer buttonId;
        private String buttonText;
    }
}
