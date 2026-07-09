package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.UpdateGroupSettingsReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Log4j2
public class UpdateGroupSettingsExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new UpdateGroupSettingsExample();
        example.lockGroupExample(greenApi);
        example.unlockGroupExample(greenApi);
    }

    /**
     * Restricts the group so that only admins can send messages and change settings.
     */
    private void lockGroupExample(GreenApi greenApi) {
        var response = greenApi.groups.updateGroupSettings(
            UpdateGroupSettingsReq.builder()
                .groupId("79001234567-1234567890@g.us")
                .allowParticipantsSendMessages(false)
                .allowParticipantsEditGroupSettings(false)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            var body = Objects.requireNonNull(response.getBody());
            if (Boolean.TRUE.equals(body.getUpdateGroupSettings())) {
                log.info("Group locked: only admins can write");
            } else {
                log.warn("Settings not changed: " + body.getReason());
            }
        } else {
            log.warn("Request failed, status: " + response.getStatusCode());
        }
    }

    /**
     * Allows all participants to send messages and edit group settings.
     */
    private void unlockGroupExample(GreenApi greenApi) {
        var response = greenApi.groups.updateGroupSettings(
            UpdateGroupSettingsReq.builder()
                .groupId("79001234567-1234567890@g.us")
                .allowParticipantsSendMessages(true)
                .allowParticipantsEditGroupSettings(true)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            var body = Objects.requireNonNull(response.getBody());
            log.info("Group unlocked: " + body.getUpdateGroupSettings());
        } else {
            log.warn("Request failed, status: " + response.getStatusCode());
        }
    }
}
