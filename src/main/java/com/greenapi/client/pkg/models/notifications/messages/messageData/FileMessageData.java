package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMessageData extends QuotedMessage {
    private String downloadUrl;
    private String caption;
    private String fileName;
    private String jpegThumbnail;
    private String mimeType;
    private String isForwarded;
    private Integer forwardingScore;
}
