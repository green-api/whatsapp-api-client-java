package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageReq {
    private final String chatId;
    private final String idMessage;
}
