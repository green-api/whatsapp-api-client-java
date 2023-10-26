package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListMessageData {
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
