package com.greenapi.client.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceSettings {
    private final String webhookUrl;
    private final String webhookUrlToken;
    private final long delaySendMessagesMilliseconds;
    private final boolean markIncomingMessagesReaded;
    private final boolean markIncomingMessagesReadedOnReply;
    private final boolean sharedSession;
    private final String proxyInstance;
    private final boolean outgoingWebhook;
    private final boolean outgoingMessageWebhook;
    private final boolean outgoingAPIMessageWebhook;
    private final boolean incomingWebhook;
    private final boolean deviceWebhook;
    private final boolean statusInstanceWebhook;
    private final boolean stateWebhook;
    private final boolean enableMessagesHistory;
    private final boolean keepOnlineStatus;
}
