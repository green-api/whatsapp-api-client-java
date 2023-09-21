package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtendedTextMessageData {
    private String text;
    private String description;
    private String title;
    private String jpegThumbnail;
    private String isForwarded;
    private Integer forwardingScore;
    private String previewType;
    private boolean containsAutoReply;
    private String mediaType;
    private boolean showAdAttribution;
    private String sourceId;
    private String sourceType;
    private String sourceUrl;
    private String thumbnailUrl;
    private String stanzaId;
    private String participant;
    private String typeMessage;
}
