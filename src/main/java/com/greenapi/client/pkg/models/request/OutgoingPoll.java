package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenapi.client.pkg.models.Option;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingPoll extends Outgoing {
    private final String message;
    private final List<Option> options;
    private final Boolean multipleAnswers;
}
