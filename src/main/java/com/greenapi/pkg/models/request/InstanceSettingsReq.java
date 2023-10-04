package com.greenapi.pkg.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceSettingsReq {
    private final String webhookUrl;
    private final String webhookUrlToken;
    private final Integer delaySendMessagesMilliseconds;
    private final String markIncomingMessagesReaded;
    private final String markIncomingMessagesReadedOnReply;
    private final String sharedSession;
    private final String proxyInstance;
    private final String outgoingWebhook;
    private final String outgoingMessageWebhook;
    private final String outgoingAPIMessageWebhook;
    private final String incomingWebhook;
    private final String deviceWebhook;
    private final String statusInstanceWebhook;
    private final String stateWebhook;
    private final String enableMessagesHistory;
    private final String keepOnlineStatus;
}
