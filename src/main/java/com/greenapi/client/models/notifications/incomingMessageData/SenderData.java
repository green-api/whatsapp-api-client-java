package com.greenapi.client.models.notifications.incomingMessageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class SenderData {
    private final Long chatId;
    private final String sender;
    private final String chatName;
    private final String senderName;
}
