package com.greenapi.client.pkg.models.notifications.messages.messageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationMessageData {
    private Double latitude;
    private Double longitude;
    private String jpegThumbnail;
    private Boolean isForwarded;
    private Integer forwardingScore;
    private String nameLocation;
    private String address;
}
