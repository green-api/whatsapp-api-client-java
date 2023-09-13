package com.greenapi.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetProfilePictureResp {
    private boolean setProfilePicture;
    private String urlAvatar;
    private String reason;
}
