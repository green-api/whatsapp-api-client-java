package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollMessageData {
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
