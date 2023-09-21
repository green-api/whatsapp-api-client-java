package com.greenapi.pkg.models.notifications.messages.messageData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMessageData {
    private Double latitude;
    private Double longitude;
    private String jpegThumbnail;
    private boolean isForwarded;
    private Integer forwardingScore;
}
