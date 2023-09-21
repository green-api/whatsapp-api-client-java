package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMessageData {
    private String downloadUrl;
    private String caption;
    private String fileName;
    private String jpegThumbnail;
    private String mimeType;
    private boolean isForwarded;
    private Integer forwardingScore;
}
