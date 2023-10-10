package com.greenapi.client.pkg.models.notifications.messages.messageData;

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
public class LocationMessageData extends QuotedMessage {
    private Double latitude;
    private Double longitude;
    private String jpegThumbnail;
    private Boolean isForwarded;
    private Integer forwardingScore;
}