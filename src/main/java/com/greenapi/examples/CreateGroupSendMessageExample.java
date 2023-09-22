package com.greenapi.examples;

import com.greenapi.pkg.api.GreenApi;
import com.greenapi.pkg.models.request.CreateGroupReq;
import com.greenapi.pkg.models.request.OutgoingMessage;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
class CreateGroupSendMessageExample {

    private void createGroupAndSendMessage(GreenApi greenApi) {
        var groupMembers = new ArrayList<String>();
        groupMembers.add("11001234567@c.us");
        groupMembers.add("11001234566@c.us");
        groupMembers.add("11001234565@c.us");

        var group = greenApi.groups.createGroup(
            CreateGroupReq.builder()
                .groupName("Test Group")
                .chatIds(groupMembers)
                .build())
            .getBody();

        if (group != null) {
            var message = greenApi.sending.sendMessage(
                OutgoingMessage.builder()
                    .chatId(group.getChatId())
                    .message("hola a todos")
                    .build())
                .getBody();

            if (message != null) {
                log.info("Create group: " + group.isCreated() +
                    "\nSend message: " + message.getIdMessage());
            }
        }
    }
}
