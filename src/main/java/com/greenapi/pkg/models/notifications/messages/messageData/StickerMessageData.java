package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StickerMessageData extends QuotedMessage {
    private String downloadUrl;
    private boolean isAnimated;
    private String jpegThumbnail;
    private String mimeType;
    private boolean isForwarded;
    private Integer forwardingScore;
}
