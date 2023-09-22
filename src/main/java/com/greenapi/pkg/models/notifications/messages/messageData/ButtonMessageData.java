package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
