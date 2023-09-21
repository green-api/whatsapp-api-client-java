package com.greenapi.pkg.models.notifications.messages.messageData;

import com.greenapi.pkg.models.Button;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ButtonMessageData {
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
