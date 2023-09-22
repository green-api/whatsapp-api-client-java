package com.greenapi.pkg.models.notifications.messages;

import com.greenapi.pkg.models.notifications.messages.messageData.TemplateButtonReplyMessage;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateButtonSelection {
    private String typeMessage;
    private TemplateButtonReplyMessage templateButtonReplyMessage;
}
