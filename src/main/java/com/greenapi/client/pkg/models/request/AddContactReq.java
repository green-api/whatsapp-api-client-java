package com.greenapi.client.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddContactReq {
    private final String chatId;
    private final String firstName;
    private final String lastName;
    private final Boolean saveInAddressbook;
}
