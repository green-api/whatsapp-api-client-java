package com.greenapi.client.models.notifications.incomingMessageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class AvatarInfo {
    private final boolean chatId;
    private final boolean existsWhatsapp;
    private final String urlAvatar;
    private final String reason;
}
