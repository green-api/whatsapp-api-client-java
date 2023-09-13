package com.greenapi.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAvatarResp {
    private String urlAvatar;
    private String reason;
    private boolean existsWhatsapp;
}
