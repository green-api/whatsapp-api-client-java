package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMessageData {
    private String downloadUrl;
    private String caption;
    private String fileName;
    private String jpegThumbnail;
    private String mimeType;
    private String isForwarded;
    private Integer forwardingScore;
}
