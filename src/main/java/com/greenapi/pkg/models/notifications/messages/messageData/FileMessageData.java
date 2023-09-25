package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FileMessageData extends QuotedMessage {
    private String downloadUrl;
    private String caption;
    private String fileName;
    private String jpegThumbnail;
    private String mimeType;
    private String isForwarded;
    private Integer forwardingScore;
}
