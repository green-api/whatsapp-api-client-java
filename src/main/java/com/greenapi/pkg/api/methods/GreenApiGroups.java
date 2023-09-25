package com.greenapi.pkg.api.methods;

import com.greenapi.pkg.models.request.ChangeGroupNameReq;
import com.greenapi.pkg.models.request.ChangeGroupPictureReq;
import com.greenapi.pkg.models.request.ChangeParticipantReq;
import com.greenapi.pkg.models.request.CreateGroupReq;
import com.greenapi.pkg.models.response.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class GreenApiGroups {
    private String host;
    private String instanceId;
    private String instanceToken;
    private RestTemplate restTemplate;

    /**
     * The method is aimed for creating a group chat.
     * https://greenapi.com/en/docs/api/groups/CreateGroup/
     */
    public ResponseEntity<CreateGroupResp> createGroup(CreateGroupReq createGroupReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/createGroup/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(createGroupReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, CreateGroupResp.class);
    }

    /**
     * The method changes a group chat name.
     * https://greenapi.com/en/docs/api/groups/UpdateGroupName/
     */
    public ResponseEntity<ChangeGroupNameResp> updateGroupName(ChangeGroupNameReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/updateGroupName/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, ChangeGroupNameResp.class);
    }

    /**
     * The method gets group chat data.
     * https://greenapi.com/en/docs/api/groups/GetGroupData/
     */
    public ResponseEntity<GroupData> getGroupData(String groupId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getGroupData/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("groupId", groupId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, GroupData.class);
    }

    /**
     * The method adds a participant to a group chat.
     * https://greenapi.com/en/docs/api/groups/AddGroupParticipant/
     */
    public ResponseEntity<AddGroupParticipantResp> addGroupParticipant(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/addGroupParticipant/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, AddGroupParticipantResp.class);
    }

    /**
     * The method removes a participant from a group chat.
     * https://greenapi.com/en/docs/api/groups/RemoveGroupParticipant/
     */
    public ResponseEntity<RemoveGroupParticipantResp> removeGroupParticipant(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/removeGroupParticipant/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, RemoveGroupParticipantResp.class);
    }

    /**
     * The method sets a group chat participant as an administrator.
     * https://greenapi.com/en/docs/api/groups/SetGroupAdmin/
     */
    public ResponseEntity<SetGroupAdminResp> setGroupAdmin(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setGroupAdmin/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SetGroupAdminResp.class);
    }

    /**
     * The method removes a participant from group chat administration rights.
     * https://greenapi.com/en/docs/api/groups/RemoveAdmin/
     */
    public ResponseEntity<RemoveGroupAdminResp> removeGroupAdmin(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/removeAdmin/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, RemoveGroupAdminResp.class);
    }

    /**
     * The method sets a group picture.
     * https://greenapi.com/en/docs/api/groups/SetGroupPicture/
     */
    public ResponseEntity<SetGroupPictureResp> setGroupPicture(ChangeGroupPictureReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setGroupPicture/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("file", new FileSystemResource(dto.getFile()));
        form.add("groupId", dto.getGroupId());

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, SetGroupPictureResp.class);
    }

    /**
     * The method makes the current account user leave the group chat.
     * https://greenapi.com/en/docs/api/groups/LeaveGroup/
     */
    public ResponseEntity<LeaveGroupResp> leaveGroup(String groupId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/leaveGroup/")
            .append(instanceToken);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("groupId", groupId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, LeaveGroupResp.class);
    }
}