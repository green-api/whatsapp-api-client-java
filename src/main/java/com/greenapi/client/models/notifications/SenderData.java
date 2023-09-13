package com.greenapi.client.models.notifications;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class SenderData {
    private final Long chatId;
    private final String sender;
    private final String chatName;
    private final String senderName;
}
