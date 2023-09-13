package com.greenapi.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetContactInfoResp {
    private String avatar;
    private String name;
    private String email;
    private String category;
    private String description;
    private List<Product> products;
    private String chatId;
    private String lastSeen;
    private boolean isArchive;
    private boolean isDisappearing;
    private boolean isMute;
    private Integer messageexpiration;
    private Integer muteexpiration;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Product {
        private String id;
        private ImageUrls imageUrls;
        private String availability;
        private ReviewStatus reviewStatus;
        private String name;
        private String description;
        private String price;
        private boolean isHidden;
    }

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
