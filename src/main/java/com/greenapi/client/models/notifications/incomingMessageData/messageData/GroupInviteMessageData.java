package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class GroupInviteMessageData {
    private final String groupJid;
    private final String inviteCode;
    private final String inviteExpiration;
    private final String groupName;
    private final String caption;
    private final boolean name;
    private final Integer jpegThumbnail;
}
