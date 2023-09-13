package com.greenapi.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetDisappearingChatResp {
    private String chatId;
    private boolean disappearingMessagesInChat;
    private Long ephemeralExpiration;
}
