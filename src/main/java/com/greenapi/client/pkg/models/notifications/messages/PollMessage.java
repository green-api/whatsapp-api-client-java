package com.greenapi.client.pkg.models.notifications.messages;

import com.greenapi.client.pkg.models.notifications.messages.messageData.PollMessageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollMessage {
    private String typeMessage;
    private PollMessageData pollMessageData;
}
