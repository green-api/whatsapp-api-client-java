package com.greenapi.pkg.models.notifications.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SenderData {
    private String chatId;
    private String sender;
    private String chatName;
    private String senderName;
}
