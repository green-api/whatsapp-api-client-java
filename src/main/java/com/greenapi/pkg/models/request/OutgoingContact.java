package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenapi.pkg.models.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingContact extends Outgoing {
    private final Contact contact;
}
