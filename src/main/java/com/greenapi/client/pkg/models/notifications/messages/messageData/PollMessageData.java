package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.Option;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollMessageData {
    private String name;
    private List<Option> options;
    private boolean multipleAnswers;
}
