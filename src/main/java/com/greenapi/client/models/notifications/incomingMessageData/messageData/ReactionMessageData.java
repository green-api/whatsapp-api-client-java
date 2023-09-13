package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ReactionMessageData {
    private final String text;
}