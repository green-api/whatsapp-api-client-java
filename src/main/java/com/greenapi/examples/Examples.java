package com.greenapi.examples;

import com.greenapi.client.GreenApiClient;
import com.greenapi.client.dto.request.CreateGroupReq;
import com.greenapi.client.dto.request.OutgoingMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Log4j2
class Examples {
    public void createGroupAndSendMessage(GreenApiClient greenApiClient, ArrayList<String> chatIds) {

        var groupResponse = greenApiClient.groups.createGroup(
            CreateGroupReq.builder()
                .groupName("Example Group")
                .chatIds(chatIds)
                .build());

        if (groupResponse.getStatusCode().is2xxSuccessful()) {
            var messageResponse = greenApiClient.sending.sendMessage(
                OutgoingMessage.builder()
                    .chatId(groupResponse.getBody().getChatId())
                    .message("Hola a todos")
                    .build());

            if (messageResponse.getStatusCode().is2xxSuccessful()) {
                log.info(
                    "\nCreate group: " + groupResponse.getBody().isCreated() +
                    "\nSent message id: " + messageResponse.getBody().getIdMessage()
                );

            } else {
                log.warn("Couldn't send a message. Status code: " + messageResponse.getStatusCode());
            }

        } else {
            log.warn("Couldn't create a group. Status code: " + groupResponse.getStatusCode());
        }
    }
}
