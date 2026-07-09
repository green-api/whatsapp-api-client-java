package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingMessage extends Outgoing {
    private final String message;
    private final Boolean linkPreview;
    private final String typePreview;
    private final CustomPreview customPreview;
    private final Integer typingTime;
}
