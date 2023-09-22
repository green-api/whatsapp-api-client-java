package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ListMessageData extends QuotedMessage {
    private String contentText;
    private String title;
    private String footer;
    private String buttonText;
    private List<Section> sections;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Section {
        private String title;
        private List<Row> rows;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Row {
        private String rowId;
        private String title;
        private String description;
    }
}
