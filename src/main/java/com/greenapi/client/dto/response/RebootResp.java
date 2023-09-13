package com.greenapi.client.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RebootResp {
    private boolean isReboot;
}
