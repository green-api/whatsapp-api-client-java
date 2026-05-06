package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.AddContactReq;
import com.greenapi.client.pkg.models.request.EditContactReq;
import com.greenapi.client.pkg.models.request.DeleteContactReq;
import com.greenapi.client.pkg.models.response.AddContactResp;
import com.greenapi.client.pkg.models.response.EditContactResp;
import com.greenapi.client.pkg.models.response.DeleteContactResp;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class GreenApiContacts {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method adds a new contact.
     * https://greenapi.com/en/docs/api/contacts/AddContact/
     */
    public ResponseEntity<AddContactResp> addContact(AddContactReq addContactReq) {

        String url = host +
            "/waInstance" + instanceId +
            "/addContact/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(addContactReq, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method edits an existing contact.
     * https://greenapi.com/en/docs/api/contacts/EditContact/
     */
    public ResponseEntity<EditContactResp> editContact(EditContactReq editContactReq) {

        String url = host +
            "/waInstance" + instanceId +
            "/editContact/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(editContactReq, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });
    }

    /**
     * The method deletes a contact.
     * https://greenapi.com/en/docs/api/contacts/DeleteContact/
     */
    public ResponseEntity<DeleteContactResp> deleteContact(DeleteContactReq deleteContactReq) {

        String url = host +
            "/waInstance" + instanceId +
            "/deleteContact/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(deleteContactReq, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
        });
    }
}