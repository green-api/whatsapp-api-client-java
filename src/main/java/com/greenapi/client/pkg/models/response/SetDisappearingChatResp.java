package com.greenapi.client.pkg.models.response;

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
    private Boolean disappearingMessagesInChat;
    private Long ephemeralExpiration;
}
