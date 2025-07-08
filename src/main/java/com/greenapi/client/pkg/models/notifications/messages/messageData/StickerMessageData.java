package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StickerMessageData {
    private String downloadUrl;
    private String caption;
    private String fileName;
    private Boolean isAnimated;
    private String jpegThumbnail;
    private String mimeType;
    private Boolean isForwarded;
    private Integer forwardingScore;
}
