package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractiveButton {
    private final String type;
    private final String buttonId;
    private final String buttonText;
    private final String copyCode;
    private final String phoneNumber;
    private final String url;
}
