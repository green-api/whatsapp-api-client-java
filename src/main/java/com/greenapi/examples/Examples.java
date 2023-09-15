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

    public void createGroupAndSendMessage(GreenApiClient greenApiClient, String chatId) {
        var chatIds = new ArrayList<String>();
        chatIds.add(chatId);

        var group = greenApiClient.groups.createGroup(
                CreateGroupReq.builder()
                    .groupName("Test Group")
                    .chatIds(chatIds)
                    .build())
            .getBody();

        if (group != null) {
            var message = greenApiClient.sending.sendMessage(
                    OutgoingMessage.builder()
                        .chatId(group.getChatId())
                        .message("test message")
                        .build())
                .getBody();

            if (message != null) {

                log.info("Create group: " + group.isCreated() +
                    "\nSend message: " + message.getIdMessage());
            }
        }
    }
}
