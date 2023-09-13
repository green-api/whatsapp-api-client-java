package com.greenapi.client.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenapi.client.models.Contact;
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
