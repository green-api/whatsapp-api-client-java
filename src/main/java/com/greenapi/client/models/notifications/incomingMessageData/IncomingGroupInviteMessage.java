package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.GroupInviteMessageData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class IncomingGroupInviteMessage implements IncomingMessageData {
    private final String typeMessage;
    private final GroupInviteMessageData groupInviteMessageData;
    private final QuotedMessage quotedMessage;
}
