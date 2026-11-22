package com.greenapi.client.pkg.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallRecordingResp {
    private String status;
    private String downloadUrl;
    private Long expireAt;
    private Long durationMs;
    private Long sizeBytes;
}
