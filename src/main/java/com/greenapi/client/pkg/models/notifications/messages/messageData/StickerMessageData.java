package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StickerMessageData {
    private String downloadUrl;
    private Boolean isAnimated;
    private String jpegThumbnail;
    private String mimeType;
    private Boolean isForwarded;
    private Integer forwardingScore;
}
