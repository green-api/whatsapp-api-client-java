package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.Option;
import com.greenapi.client.pkg.models.request.OutgoingPoll;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
public class SendPollExample {
    private void sendPollExample(GreenApi greenApi) {
        var options = new ArrayList<Option>();
        options.add(new Option("option 1"));
        options.add(new Option("option 2"));
        options.add(new Option("option 3"));

        var dto = OutgoingPoll.builder()
            .chatId("111111111111@c.us")
            .message("text message")
            .options(options)
            .multipleAnswers(false)
            .build();

        var response = greenApi.sending.sendPoll(dto);
        log.info(response);
    }
}
