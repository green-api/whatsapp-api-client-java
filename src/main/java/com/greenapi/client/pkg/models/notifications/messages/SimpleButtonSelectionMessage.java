package com.greenapi.client.pkg.models.notifications.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
