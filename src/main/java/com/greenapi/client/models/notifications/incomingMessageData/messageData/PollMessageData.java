package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class PollMessageData {
    private final String name;
    private final Option options;
    private final Integer selectableOptionsCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Option {
        private String optionName;
    }
}
