package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StickerMessageData {
    private String downloadUrl;
    private boolean isAnimated;
    private String jpegThumbnail;
    private String mimeType;
    private boolean isForwarded;
    private Integer forwardingScore;
}
