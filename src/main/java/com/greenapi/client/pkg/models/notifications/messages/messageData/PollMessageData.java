package com.greenapi.client.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
