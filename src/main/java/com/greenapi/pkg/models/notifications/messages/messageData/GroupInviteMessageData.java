package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GroupInviteMessageData extends QuotedMessage {
    private String groupJid;
    private String inviteCode;
    private String inviteExpiration;
    private String groupName;
    private String caption;
    private Boolean name;
    private Integer jpegThumbnail;
}
