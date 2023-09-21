package com.greenapi.examples;

import com.greenapi.pkg.api.GreenApi;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Log4j2
public class UploadExample {
    private void uploadExample(GreenApi greenApi) throws IOException {
        var file = new File("User/username/folder/Go-Logo_Blue.svg");

        var response = greenApi.sending.uploadFile(file);

        if (response.getStatusCode().isError()) {
            log.warn("message sending is failed");
        }

        log.info("message sent, file url: " + Objects.requireNonNull(response.getBody()).getUrlFile());
    }
}
