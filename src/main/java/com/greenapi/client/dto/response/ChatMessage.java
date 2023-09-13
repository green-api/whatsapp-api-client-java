package com.greenapi.client.dto.response;

import com.greenapi.client.models.ChatContact;
import com.greenapi.client.models.ChatLocation;
import com.greenapi.client.models.ExtendedTextMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String idMessage;
    private Long timestamp;
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
    private ChatMessage quotedMessage;
}
