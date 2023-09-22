package com.greenapi.examples;

import com.greenapi.pkg.api.GreenApi;
import com.greenapi.pkg.models.request.OutgoingFileByUpload;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Objects;

@Log4j2
public class SendFileByUploadExample {

    private void sendFileByUploadExample(GreenApi greenApi) {
        var file = new File("User/username/folder/Go-Logo_Blue.svg");

        var response = greenApi.sending.sendFileByUpload(OutgoingFileByUpload.builder()
            .file(file)
            .fileName(file.getName())
            .chatId("11001234567@c.us")
            .build());

        if (response.getStatusCode().isError()) {
            log.warn("message sending is failed");
        }

        log.info("message sent, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
    }
}
