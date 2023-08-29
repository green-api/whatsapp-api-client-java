package com.greenapi.client.methods;

import com.greenapi.client.dto.request.InstanceSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Component(value = "account")
public class GreenApiAccount {
    @Value("${green-api.host}")
    private String host;
    @Value("${green-api.instanceId}")
    private String instanceId;
    @Value("${green-api.token}")
    private String token;
    @Autowired
    private RestTemplate restTemplate;

    /**This method return instance settings https://green-api.com/en/docs/api/account/GetSettings/*/
    public ResponseEntity<String> getSettings() {
        var requestUrl = new StringBuilder();

        requestUrl
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getSettings/")
            .append(token);

        return restTemplate.exchange(requestUrl.toString(), HttpMethod.GET, null, String.class);
    }

    /**Use this method for change instance settings https://green-api.com/en/docs/api/account/SetSettings/*/
    public ResponseEntity<String> setSetting(InstanceSettings instanceSettings) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setSettings/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(instanceSettings, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method is aimed for getting the account state
     * https://green-api.com/en/docs/api/account/GetStateInstance/*/
    public ResponseEntity<String> getStateInstance() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getStateInstance/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for getting the status of the account instance socket connection with WhatsApp.
     * https://green-api.com/en/docs/api/account/GetStatusInstance/*/
    public ResponseEntity<String> getStatusInstance() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getStatusInstance/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for rebooting an account.
     * https://green-api.com/en/docs/api/account/Reboot/*/
    public ResponseEntity<String> reboot() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/reboot/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for logging out an account.
     * https://green-api.com/en/docs/api/account/Logout/*/
    public ResponseEntity<String> logout() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/logout/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for getting QR code.
     * To authorize your account, you have to scan a QR code from application WhatsApp Business on your phone.
     * https://green-api.com/en/docs/api/account/QR/*/
    public ResponseEntity<String> getQrCode() {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/qr/")
            .append(token);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, String.class);
    }

    /**The method is aimed for setting an account picture.
     * https://green-api.com/en/docs/api/account/SetProfilePicture/*/
    public ResponseEntity<String> setProfilePicture(File file) {
        var restTemplate = new RestTemplate();
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setProfilePicture/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("file", new FileSystemResource(file));

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }
}
