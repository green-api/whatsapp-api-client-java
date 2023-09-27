package com.greenapi.pkg.models.notifications.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListSelectionMessage {
    private String typeMessage;
    private ListResponseMessage listResponseMessage;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class ListResponseMessage {
        private String title;
        private String listType;
        private String singleSelectReply;
        private String stanzaId;
    }
}
