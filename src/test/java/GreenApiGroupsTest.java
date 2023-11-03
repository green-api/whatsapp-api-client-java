import com.greenapi.client.pkg.models.request.ChangeGroupNameReq;
import com.greenapi.client.pkg.models.request.ChangeGroupPictureReq;
import com.greenapi.client.pkg.models.request.ChangeParticipantReq;
import com.greenapi.client.pkg.models.request.CreateGroupReq;
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
        membersList.add(chatId);

        var dto = CreateGroupReq.builder()
            .groupName("GREENGROUP")
            .chatIds(membersList)
            .build();

        var response = greenApi.groups.createGroup(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateGroupName() {
        var dto = ChangeGroupNameReq.builder()
            .groupName("GREENGROUP1")
            .groupId(groupChatId)
            .build();

        var response = greenApi.groups.updateGroupName(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getGroupData() {
        var response = greenApi.groups.getGroupData(groupChatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeGroupParticipant() {
        var dto = ChangeParticipantReq.builder()
            .groupId(groupChatId)
            .participantChatId(chatId)
            .build();

        var response = greenApi.groups.removeGroupParticipant(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addGroupParticipant() {
        var dto = ChangeParticipantReq.builder()
            .groupId(groupChatId)
            .participantChatId(chatId)
            .build();

        var response = greenApi.groups.addGroupParticipant(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setGroupAdmin() {
        var dto = ChangeParticipantReq.builder()
            .groupId(groupChatId)
            .participantChatId(chatId)
            .build();

        var response = greenApi.groups.setGroupAdmin(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeAdmin() {
        var dto = ChangeParticipantReq.builder()
            .groupId(groupChatId)
            .participantChatId(chatId)
            .build();

        var response = greenApi.groups.removeGroupAdmin(dto);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setGroupPicture() {
        var file = new File(fileUrl);
        var dto = ChangeGroupPictureReq.builder()
            .file(file)
            .groupId(groupChatId)
            .build();

        var response = greenApi.groups.setGroupPicture(dto);
        log.info(response);
    }

    /*@Test
    void leaveGroup() {
        var response = greenApiClient.groups.leaveGroup(groupChatId);
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/
}