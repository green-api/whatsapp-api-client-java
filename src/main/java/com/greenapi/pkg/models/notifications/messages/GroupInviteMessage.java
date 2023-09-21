package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.GroupInviteMessageData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInviteMessage {
    private String typeMessage;
    private GroupInviteMessageData groupInviteMessageData;
    private QuotedMessage quotedMessage;
}
