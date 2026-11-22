package com.greenapi.client.pkg.models.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import com.greenapi.client.pkg.models.response.CallParticipant;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutgoingCall extends NotificationBody {
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private String from;
    private String status;
    private Boolean isVideo;
    private Integer duration;
    private List<CallParticipant> participants;
    private Boolean sendByApi;
}
