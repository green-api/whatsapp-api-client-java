package com.greenapi.client.pkg.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallRecord {
    private String type;
    private String idMessage;
    private Long timestamp;
    private String typeMessage;
    private String chatId;
    private Boolean isVideo;
    private String status;
    private Boolean isGroup;
}
