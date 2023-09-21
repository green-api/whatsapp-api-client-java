package com.greenapi.pkg.models.notifications.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarInfo {
    private boolean chatId;
    private boolean existsWhatsapp;
    private String urlAvatar;
    private String reason;
}
