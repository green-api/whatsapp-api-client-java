package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenapi.client.pkg.models.TemplateButtons;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingTemplateButtons extends Outgoing {
    private final String message;
    private final String footer;
    private final List<TemplateButtons> templateButtons;
}
