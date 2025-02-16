package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteMessageReq {
    private final String chatId;
    private final String idMessage;
    private Boolean onlySenderDelete;

    // MessageReq -> DeleteMessageReq
    public static DeleteMessageReq from(MessageReq messageReq) {
        return DeleteMessageReq.builder()
            .chatId(messageReq.getChatId())
            .idMessage(messageReq.getIdMessage())
            .build();
    }
}