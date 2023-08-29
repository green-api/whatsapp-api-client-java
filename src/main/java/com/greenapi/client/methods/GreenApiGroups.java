package com.greenapi.client.methods;

import com.greenapi.client.dto.request.ChangeGroupNameReq;
import com.greenapi.client.dto.request.ChangeGroupPictureReq;
import com.greenapi.client.dto.request.ChangeParticipantReq;
import com.greenapi.client.dto.request.CreateGroupReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("groups")
public class GreenApiGroups {
    @Value("${green-api.host}")
    private String host;
    @Value("${green-api.instanceId}")
    private String instanceId;
    @Value("${green-api.token}")
    private String token;
    @Autowired
    private RestTemplate restTemplate;

    /**The method is aimed for creating a group chat.
     * https://green-api.com/en/docs/api/groups/CreateGroup/*/
    public ResponseEntity<String> createGroup(CreateGroupReq createGroupReq) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/createGroup/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(createGroupReq, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method changes a group chat name.
     * https://green-api.com/en/docs/api/groups/UpdateGroupName/*/
    public ResponseEntity<String> updateGroupName(ChangeGroupNameReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/updateGroupName/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method gets group chat data.
     * https://green-api.com/en/docs/api/groups/GetGroupData/*/
    public ResponseEntity<String> getGroupData(String groupId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/getGroupData/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("groupId", groupId);

        var requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method adds a participant to a group chat.
     * https://green-api.com/en/docs/api/groups/AddGroupParticipant/*/
    public ResponseEntity<String> addGroupParticipant(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/addGroupParticipant/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method removes a participant from a group chat.
     * https://green-api.com/en/docs/api/groups/RemoveGroupParticipant/*/
    public ResponseEntity<String> removeGroupParticipant(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/removeGroupParticipant/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method sets a group chat participant as an administrator.
     * https://green-api.com/en/docs/api/groups/SetGroupAdmin/*/
    public ResponseEntity<String> setGroupAdmin(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setGroupAdmin/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method removes a participant from group chat administration rights.
     * https://green-api.com/en/docs/api/groups/RemoveAdmin/*/
    public ResponseEntity<String> removeGroupAdmin(ChangeParticipantReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/removeAdmin/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method sets a group picture.
     * https://green-api.com/en/docs/api/groups/SetGroupPicture/*/
    public ResponseEntity<String> setGroupPicture(ChangeGroupPictureReq dto) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/setGroupPicture/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var form = new LinkedMultiValueMap<>();
        form.add("file", new FileSystemResource(dto.getFile()));
        form.add("groupId", dto.getGroupId());

        var requestEntity = new HttpEntity<>(form, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }

    /**The method makes the current account user leave the group chat.
     * https://green-api.com/en/docs/api/groups/LeaveGroup/*/
    public ResponseEntity<String> leaveGroup(String groupId) {
        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(host)
            .append("/waInstance").append(instanceId)
            .append("/leaveGroup/")
            .append(token);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(groupId, headers);

        return restTemplate.exchange(stringBuilder.toString(), HttpMethod.POST, requestEntity, String.class);
    }


}
