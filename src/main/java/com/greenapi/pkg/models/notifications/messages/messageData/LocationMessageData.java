package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LocationMessageData extends QuotedMessage {
    private Double latitude;
    private Double longitude;
    private String jpegThumbnail;
    private boolean isForwarded;
    private Integer forwardingScore;
}
