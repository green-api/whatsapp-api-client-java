package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.OutgoingFileByUrl;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Log4j2
public class UploadFileAndSendByUrlExample {

    private void uploadExample(GreenApi greenApi) throws IOException {
        var file = new File("User/username/folder/Go-Logo_Blue.svg");

        var uploadFileResp = greenApi.sending.uploadFile(file);
        if (uploadFileResp.getStatusCode().isError()) {
            log.error("upload file failed");
        }

        var sendUrlResp = greenApi.sending.sendFileByUrl(
            OutgoingFileByUrl.builder()
                .urlFile(Objects.requireNonNull(uploadFileResp.getBody()).getUrlFile())
                .build());

        log.info("file sent, message id: " + Objects.requireNonNull(sendUrlResp.getBody()).getIdMessage());
    }
}
