package com.greenapi.client.methods;

import com.greenapi.client.dto.request.ChangeGroupNameReq;
import com.greenapi.client.dto.request.ChangeGroupPictureReq;
import com.greenapi.client.dto.request.CreateGroupReq;
import com.greenapi.client.dto.request.ChangeParticipantReq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.ArrayList;

@Log4j2
class GreenApiGroupsTest extends GreenApiTest {

    @Test
    void createGroup() {
        var membersList = new ArrayList<String>();
        membersList.add("79851150769@c.us");

        var dto = CreateGroupReq.builder()
            .groupName("GREENGROUP")
            .chatIds(membersList)
            .build();

        var response = greenApiClient.groups.createGroup(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateGroupName() {
        var dto = ChangeGroupNameReq.builder()
            .groupName("GREENGROUP1")
            .groupId("120363169960827018@g.us")
            .build();

        var response = greenApiClient.groups.updateGroupName(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getGroupData() {
        var response = greenApiClient.groups.getGroupData("120363169960827018@g.us");
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeGroupParticipant() {
        var dto = ChangeParticipantReq.builder()
            .groupId("120363169960827018@g.us")
            .participantChatId("79851150769@c.us")
            .build();

        var response = greenApiClient.groups.removeGroupParticipant(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addGroupParticipant() {
        var dto = ChangeParticipantReq.builder()
            .groupId("120363169960827018@g.us")
            .participantChatId("79851150769@c.us")
            .build();

        var response = greenApiClient.groups.addGroupParticipant(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setGroupAdmin() {
        var dto = ChangeParticipantReq.builder()
            .groupId("120363169960827018@g.us")
            .participantChatId("79851150769@c.us")
            .build();

        var response = greenApiClient.groups.setGroupAdmin(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeAdmin() {
        var dto = ChangeParticipantReq.builder()
            .groupId("120363169960827018@g.us")
            .participantChatId("79851150769@c.us")
            .build();

        var response = greenApiClient.groups.removeGroupAdmin(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setGroupPicture() {
        var file = new File("/Users/kocherov/Desktop/avatarExample.jpeg");
        var dto = ChangeGroupPictureReq.builder()
            .file(file)
            .groupId("120363169960827018@g.us")
            .build();

        var response = greenApiClient.groups.setGroupPicture(dto);
        log.info(response);
    }

    /*@Test
    void leaveGroup() {
        var response = greenApiClient.groups.leaveGroup("120363169960827018@g.us");
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/
}