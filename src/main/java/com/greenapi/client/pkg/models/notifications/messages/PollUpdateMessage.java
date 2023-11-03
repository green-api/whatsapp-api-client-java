package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.PollUpdateMessageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollUpdateMessage {
    private String typeMessage;
    private PollUpdateMessageData pollMessageData;
}
