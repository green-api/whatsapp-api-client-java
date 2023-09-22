package com.greenapi.pkg.models.notifications.messages;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleButtonSelectionMessage {
    private String typeMessage;
    private ButtonsResponseMessage buttonsResponseMessage;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class ButtonsResponseMessage {
        private String selectedButtonId;
        private String selectedButtonText;
        private String stanzaId;
    }
}
