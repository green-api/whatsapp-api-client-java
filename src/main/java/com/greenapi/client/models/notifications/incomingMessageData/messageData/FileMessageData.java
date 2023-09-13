package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class FileMessageData {
    private final String downloadUrl;
    private final String caption;
    private final String fileName;
    private final String jpegThumbnail;
    private final String mimeType;
    private final boolean isForwarded;
    private final Integer forwardingScore;
}
