package com.greenapi.client.models.notifications.incomingMessageData.messageData;

import com.greenapi.client.models.ChatContact;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class ContactsArrayMessageData {
    private final List<ChatContact> contacts;
    private final boolean isForwarded;
    private final Integer forwardingScore;
}
