package com.greenapi.client.pkg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatHistoryMessage {
    private String type;
    private Long timestamp;
    private String idMessage;
    private String statusMessage;
    private String typeMessage;
    private String chatId;
    private String senderId;
    private String senderName;
    private String textMessage;
    private String downloadUrl;
    private String caption;
    private ChatLocation location;
    private ChatContact contact;
    private ExtendedTextMessage extendedTextMessage;
}
