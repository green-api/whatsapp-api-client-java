package com.greenapi.client.pkg.models.notifications.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvatarInfo {
    private Boolean chatId;
    private Boolean existsWhatsapp;
    private String urlAvatar;
    private String reason;
}
