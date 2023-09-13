package com.greenapi.client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedTextMessage {
    private String text;
    private String description;
    private String title;
    private String previewType;
    private String jpegThumbnail;
    private String stanzaId;
    private String participant;
}
