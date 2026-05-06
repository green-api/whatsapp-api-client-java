package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.AddContactReq;
import com.greenapi.client.pkg.models.request.EditContactReq;
import com.greenapi.client.pkg.models.request.DeleteContactReq;
import com.greenapi.client.pkg.models.response.AddContactResp;
import com.greenapi.client.pkg.models.response.EditContactResp;
import com.greenapi.client.pkg.models.response.DeleteContactResp;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
class ContactsMethodsExample {

    private void addContact(GreenApi greenApi) {
        var addContactReq = AddContactReq.builder()
            .chatId("11001234567@c.us")
            .firstName("John")
            .lastName("Doe")
            .build();

        var addContactResp = greenApi.contacts.addContact(addContactReq).getBody();
    }

    private void editContact(GreenApi greenApi) {
        var editContactReq = EditContactReq.builder()
            .chatId("11001234567@c.us")
            .firstName("Jane")
            .lastName("Smith")
            .build();

        var editContactResp = greenApi.contacts.editContact(editContactReq).getBody();
    }

    private void deleteContact(GreenApi greenApi) {
        var deleteContactReq = DeleteContactReq.builder()
            .chatId("11001234567@c.us")
            .build();

        var deleteContactResp = greenApi.contacts.deleteContact(deleteContactReq).getBody();
    }
}
