package com.greenapi.client.pkg.models.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greenapi.client.pkg.models.notifications.messages.InstanceData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingCall extends NotificationBody {
    private InstanceData instanceData;
    private Long timestamp;
    private String idMessage;
    private String from;
    private String status;
    private Boolean isVideo;
    private Boolean isGroup;
    private Integer duration;
    private Boolean sendByApi;
}
