package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.InstanceSettingsReq;
import com.greenapi.client.pkg.models.response.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@AllArgsConstructor
public class GreenApiAccount {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * This method return instance settings https://greenapi.com/en/docs/api/account/GetSettings/
     */
    public ResponseEntity<Settings> getSettings() {
        var requestUrl = new StringBuilder();

        requestUrl
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getSettings/")
            .append(instanceToken);

        return restTemplate.exchange(requestUrl.toString(), HttpMethod.GET, null, Settings.class);
    }

    /**
     * The method is aimed to get information about the WhatsApp account https://greenapi.com/en/api/account/GetWaSettings/
     */
    public ResponseEntity<WaSettings> getWaSettings() {
        var requestUrl = new StringBuilder();

        requestUrl
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getWaSettings/")
            .append(instanceToken);

        return restTemplate.exchange(requestUrl.toString(), HttpMethod.GET, null, WaSettings.class);
    }

    /**
     * Use this method for change instance settings https://greenapi.com/en/docs/api/account/SetSettings/
     */
    public ResponseEntity<SetSettingsResp> setSetting(InstanceSettingsReq instanceSettings) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setSettings/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(instanceSettings, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SetSettingsResp.class);
    }

    /**
     * The method is aimed for getting the account state
     * https://greenapi.com/en/docs/api/account/GetStateInstance/
     */
    public ResponseEntity<StateInstanceResp> getStateInstance() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getStateInstance/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, StateInstanceResp.class);
    }

    /**
     * The method is aimed for getting the status of the account instance socket connection with WhatsApp.
     * https://greenapi.com/en/docs/api/account/GetStatusInstance/
     */
    public ResponseEntity<StatusInstanceResp> getStatusInstance() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getStatusInstance/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, StatusInstanceResp.class);
    }

    /**
     * The method is aimed for rebooting an account.
     * https://greenapi.com/en/docs/api/account/Reboot/
     */
    public ResponseEntity<RebootResp> reboot() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/reboot/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, RebootResp.class);
    }

    /**
     * The method is aimed for logging out an account.
     * https://greenapi.com/en/docs/api/account/Logout/
     */
    public ResponseEntity<LogoutResp> logout() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/logout/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, LogoutResp.class);
    }

    /**
     * The method is aimed for getting QR code.
     * To authorize your account, you have to scan a QR code from application WhatsApp Business on your phone.
     * https://greenapi.com/en/docs/api/account/QR/
     */
    public ResponseEntity<Qr> getQrCode() {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/qr/")
            .append(instanceToken);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, null, Qr.class);
    }

    /**
     * The method is aimed for setting an account picture.
     * https://greenapi.com/en/docs/api/account/SetProfilePicture/
     */
    public ResponseEntity<SetProfilePictureResp> setProfilePicture(File file) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setProfilePicture/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("file", new FileSystemResource(file));

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SetProfilePictureResp.class);
    }
}
