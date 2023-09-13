package com.greenapi.client.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings {
    private String wid;
    private String webhookUrl;
    private long delaySendMessagesMilliseconds;
    private String markIncomingMessagesReaded;
    private String markIncomingMessagesReadedOnReply;
    private String sharedSession;
    private String proxyInstance;
    private String outgoingWebhook;
    private String outgoingMessageWebhook;
    private String outgoingAPIMessageWebhook;
    private String incomingWebhook;
    private String deviceWebhook;
    private String statusInstanceWebhook;
    private String stateWebhook;
    private String enableMessagesHistory;
    private String keepOnlineStatus;
}
