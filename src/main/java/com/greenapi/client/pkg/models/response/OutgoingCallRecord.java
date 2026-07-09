package com.greenapi.client.pkg.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutgoingCallRecord {
    private String type;
    private String idMessage;
    private Long timestamp;
    private String typeMessage;
    private String chatId;
    private Integer duration;
    private Boolean isVideo;
    private String status;
    private List<CallParticipant> participants;
}
