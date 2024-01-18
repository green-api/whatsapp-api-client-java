package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollUpdateMessageData {
    private String name;
    private List<Vote> votes;
    private boolean multipleAnswers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Vote {
        private String optionName;
        private List<Voter> optionVoters;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Voter {
        private String wid;
    }
}
