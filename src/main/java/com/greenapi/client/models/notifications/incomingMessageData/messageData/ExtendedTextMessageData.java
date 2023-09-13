package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ExtendedTextMessageData {
    private final String text;
    private final String description;
    private final String title;
    private final String jpegThumbnail;
    private final boolean isForwarded;
    private final Integer forwardingScore;
    private final String previewType;
    private final boolean containsAutoReply;
    private final String mediaType;
    private final boolean showAdAttribution;
    private final String sourceId;
    private final String sourceType;
    private final String sourceUrl;
    private final String thumbnailUrl;
}
