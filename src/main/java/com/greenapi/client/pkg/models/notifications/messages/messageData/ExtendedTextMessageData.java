package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedTextMessageData {
    private String text;
    private String description;
    private String title;
    private String jpegThumbnail;
    private String isForwarded;
    private Integer forwardingScore;
    private String previewType;
    private Boolean containsAutoReply;
    private String mediaType;
    private Boolean showAdAttribution;
    private String sourceId;
    private String sourceType;
    private String sourceUrl;
    private String thumbnailUrl;
}
