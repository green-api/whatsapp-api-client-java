package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingListMessage extends Outgoing {
    private final String message;
    private final String title;
    private final String footer;
    private final String buttonText;
    private final List<Section> sections;

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
