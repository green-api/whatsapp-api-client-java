package com.greenapi.pkg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatLocation {
    private String nameLocation;
    private String address;
    private double latitude;
    private double longitude;
    private String jpegThumbnail;
}
