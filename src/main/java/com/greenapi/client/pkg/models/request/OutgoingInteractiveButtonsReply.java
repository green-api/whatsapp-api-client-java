package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingInteractiveButtonsReply {
    private final String chatId;
    private final String header;
    private final String body;
    private final String footer;
    private final List<InteractiveReplyButton> buttons;
}
