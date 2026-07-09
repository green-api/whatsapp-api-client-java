package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.OutgoingFileByUpload;
import com.greenapi.client.pkg.models.request.OutgoingFileByUrl;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Objects;

@Log4j2
public class SendFileWithTypingExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        var example = new SendFileWithTypingExample();
        example.sendFileByUrlWithTypingExample(greenApi);
        example.sendFileByUploadWithRecordingExample(greenApi);
    }

    /**
     * Sends a file by URL with a 3-second typing indicator shown before the file arrives.
     */
    private void sendFileByUrlWithTypingExample(GreenApi greenApi) {
        var response = greenApi.sending.sendFileByUrl(
            OutgoingFileByUrl.builder()
                .chatId("11001234567@c.us")
                .urlFile("https://example.com/report.pdf")
                .fileName("report.pdf")
                .caption("Monthly report")
                .typingTime(3000)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("File sent by URL, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("File sending failed, status: " + response.getStatusCode());
        }
    }

    /**
     * Uploads a local audio file showing a "recording audio" indicator to the recipient.
     */
    private void sendFileByUploadWithRecordingExample(GreenApi greenApi) {
        var response = greenApi.sending.sendFileByUpload(
            OutgoingFileByUpload.builder()
                .chatId("11001234567@c.us")
                .file(new File("/path/to/voice.ogg"))
                .fileName("voice.ogg")
                .typingTime(5000)
                .typingType("recording")
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Audio uploaded and sent, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        } else {
            log.warn("File upload failed, status: " + response.getStatusCode());
        }
    }
}
