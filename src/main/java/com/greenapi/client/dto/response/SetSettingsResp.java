package com.greenapi.client.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetSettingsResp {
    private String saveSettings;
}
