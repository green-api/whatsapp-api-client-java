package com.greenapi.client.models.notifications.incomingMessageData;

import com.greenapi.client.models.notifications.incomingMessageData.messageData.ButtonsResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class SimpleButtonSelection implements IncomingMessageData {
    private final String typeMessage;
    private final ButtonsResponseMessage buttonsResponseMessage;
}
