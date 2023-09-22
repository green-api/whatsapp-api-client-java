package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingLocation extends Outgoing {
    private final String nameLocation;
    private final String address;
    private final double latitude;
    private final double longitude;
}
