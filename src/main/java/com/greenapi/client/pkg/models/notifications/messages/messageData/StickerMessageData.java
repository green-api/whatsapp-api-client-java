package com.greenapi.client.pkg.models.notifications.messages.messageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StickerMessageData extends QuotedMessage {
    private String downloadUrl;
    private Boolean isAnimated;
    private String jpegThumbnail;
    private String mimeType;
    private Boolean isForwarded;
    private Integer forwardingScore;
}
