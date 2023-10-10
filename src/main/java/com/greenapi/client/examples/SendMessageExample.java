package com.greenapi.client.examples;

import com.greenapi.client.pkg.models.request.OutgoingMessage;
import com.greenapi.client.pkg.api.GreenApi;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SendMessageExample {
    private void createGroupAndSendMessage(GreenApi greenApi) {
        var message = greenApi.sending.sendMessage(
                OutgoingMessage.builder()
                    .chatId("111111111111@c.us")
                    .message("hola a todos")
                    .build());

        if (message.getStatusCode().is2xxSuccessful()) {
            log.info(message.getBody());
        } else {
            log.warn("Message isn't sent, status code: " + message.getStatusCode());
        }
    }
}
