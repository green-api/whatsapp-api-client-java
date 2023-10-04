package com.greenapi.pkg.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetProfilePictureResp {
    private Boolean setProfilePicture;
    private String urlAvatar;
    private String reason;
}
