package com.greenapi.client.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenapi.client.domain.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingButtons extends Outgoing {
    private final String message;
    private final String footer;
    private final List<Button> buttons;
}
