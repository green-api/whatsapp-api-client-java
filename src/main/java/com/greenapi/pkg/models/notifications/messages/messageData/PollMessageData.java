package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PollMessageData extends QuotedMessage {
    private String name;
    private Option options;
    private Integer selectableOptionsCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Option {
        private String optionName;
    }
}
