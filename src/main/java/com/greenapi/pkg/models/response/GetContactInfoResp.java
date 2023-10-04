package com.greenapi.pkg.models.response;

import com.greenapi.pkg.models.Product;
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
    private Boolean isArchive;
    private Boolean isDisappearing;
    private Boolean isMute;
    private Integer messageexpiration;
    private Integer muteexpiration;
}
