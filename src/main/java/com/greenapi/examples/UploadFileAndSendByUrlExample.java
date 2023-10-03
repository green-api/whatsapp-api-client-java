package com.greenapi.examples;

import com.greenapi.pkg.api.GreenApi;
import com.greenapi.pkg.models.request.OutgoingFileByUrl;
import com.greenapi.pkg.models.response.SendMessageResp;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Log4j2
public class UploadFileAndSendByUrlExample {

    private void uploadExample(GreenApi greenApi) throws IOException {
        var file = new File("User/username/folder/Go-Logo_Blue.svg");

        var response = greenApi.sending.uploadFile(file);
        if (response.getStatusCode().isError()) {
            log.error("upload file failed");
        }

        var responseEntity = greenApi.sending.sendFileByUrl(
            OutgoingFileByUrl.builder()
                .urlFile(Objects.requireNonNull(response.getBody()).getUrlFile())
                .build());

        log.info("file sent, message id: " + Objects.requireNonNull(responseEntity.getBody()).getIdMessage());
    }
}
