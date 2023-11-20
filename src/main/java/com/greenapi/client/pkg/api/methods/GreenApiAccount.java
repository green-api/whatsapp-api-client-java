package com.greenapi.client.pkg.api.methods;

import com.greenapi.client.pkg.models.request.InstanceSettingsReq;
import com.greenapi.client.pkg.models.response.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

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

        String url = host +
            "/waInstance" + instanceId +
            "/getSettings/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, Settings.class);
    }

    /**
     * The method is aimed to get information about the WhatsApp account https://greenapi.com/en/api/account/GetWaSettings/
     */
    public ResponseEntity<WaSettings> getWaSettings() {

        String url = host +
            "/waInstance" + instanceId +
            "/getWaSettings/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, WaSettings.class);
    }

    /**
     * Use this method for change instance settings https://greenapi.com/en/docs/api/account/SetSettings/
     */
    public ResponseEntity<SetSettingsResp> setSetting(InstanceSettingsReq instanceSettings) {

        String url = host +
            "/waInstance" + instanceId +
            "/setSettings/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(instanceSettings, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SetSettingsResp.class);
    }

    /**
     * The method is aimed for getting the account state
     * https://greenapi.com/en/docs/api/account/GetStateInstance/
     */
    public ResponseEntity<StateInstanceResp> getStateInstance() {

        String url = host +
            "/waInstance" + instanceId +
            "/getStateInstance/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, StateInstanceResp.class);
    }

    /**
     * The method is aimed for getting the status of the account instance socket connection with WhatsApp.
     * https://greenapi.com/en/docs/api/account/GetStatusInstance/
     */
    public ResponseEntity<StatusInstanceResp> getStatusInstance() {

        String url = host +
            "/waInstance" + instanceId +
            "/getStatusInstance/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, StatusInstanceResp.class);
    }

    /**
     * The method is aimed for rebooting an account.
     * https://greenapi.com/en/docs/api/account/Reboot/
     */
    public ResponseEntity<RebootResp> reboot() {

        String url = host +
            "/waInstance" + instanceId +
            "/reboot/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, RebootResp.class);
    }

    /**
     * The method is aimed for logging out an account.
     * https://greenapi.com/en/docs/api/account/Logout/
     */
    public ResponseEntity<LogoutResp> logout() {

        String url = host +
            "/waInstance" + instanceId +
            "/logout/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, LogoutResp.class);
    }

    /**
     * The method is aimed for getting QR code.
     * To authorize your account, you have to scan a QR code from application WhatsApp Business on your phone.
     * https://greenapi.com/en/docs/api/account/QR/
     */
    public ResponseEntity<Qr> getQrCode() {

        String url = host +
            "/waInstance" + instanceId +
            "/qr/" +
            instanceToken;

        return restTemplate.exchange(url, HttpMethod.GET, null, Qr.class);
    }

    /**
     * The method is aimed for setting an account picture.
     * https://greenapi.com/en/docs/api/account/SetProfilePicture/
     */
    public ResponseEntity<SetProfilePictureResp> setProfilePicture(File file) {

        String url = host +
            "/waInstance" + instanceId +
            "/setProfilePicture/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("file", new FileSystemResource(file));

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SetProfilePictureResp.class);
    }

    /**
     * The method is intended to authorize an instance by phone number. The method is used as an alternative to the QR method.
     * https://greenapi.com/en/docs/api/account/GetAuthorizationCode/
     */
    public ResponseEntity<GetAuthorizationCodeResp> getAuthorizationCode(Long phoneNumber) {

        String url = host +
            "/waInstance" + instanceId +
            "/GetAuthorizationCode/" +
            instanceToken;

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = new HashMap<>();
        body.put("phoneNumber", phoneNumber);

        var requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, GetAuthorizationCodeResp.class);
    }
}
