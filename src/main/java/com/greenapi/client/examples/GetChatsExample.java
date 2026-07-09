package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Log4j2
public class GetChatsExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new GetChatsExample();
        example.getAllChatsExample(greenApi);
        example.getTopChatsExample(greenApi);
    }

    /**
     * Returns all chats of the account.
     */
    private void getAllChatsExample(GreenApi greenApi) {
        var response = greenApi.service.getChats();

        if (response.getStatusCode().is2xxSuccessful()) {
            var chats = Objects.requireNonNull(response.getBody());
            log.info("Total chats: " + chats.size());
            chats.forEach(chat -> log.info(
                "Chat: " + chat.getId() + " | " + chat.getName() + " | type: " + chat.getType()));
        } else {
            log.warn("Failed to get chats, status: " + response.getStatusCode());
        }
    }

    /**
     * Returns the 10 most recently active chats.
     */
    private void getTopChatsExample(GreenApi greenApi) {
        var response = greenApi.service.getChats(10);

        if (response.getStatusCode().is2xxSuccessful()) {
            var chats = Objects.requireNonNull(response.getBody());
            log.info("Top 10 active chats:");
            chats.forEach(chat -> log.info(
                " - " + chat.getName() + " [archived: " + chat.getArchive() + "]"));
        } else {
            log.warn("Failed to get chats, status: " + response.getStatusCode());
        }
    }
}
