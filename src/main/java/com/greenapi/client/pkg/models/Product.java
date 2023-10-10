package com.greenapi.client.pkg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private ImageUrls imageUrls;
    private String availability;
    private ReviewStatus reviewStatus;
    private String name;
    private String description;
    private String price;
    private Boolean isHidden;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewStatus {
        private String whatsapp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ImageUrls {
        private String requested;
        private String original;
    }
}