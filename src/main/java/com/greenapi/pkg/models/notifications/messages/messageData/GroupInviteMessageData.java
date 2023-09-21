package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInviteMessageData {
    private String groupJid;
    private String inviteCode;
    private String inviteExpiration;
    private String groupName;
    private String caption;
    private boolean name;
    private Integer jpegThumbnail;
}
