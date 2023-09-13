package com.greenapi.client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueMessageBody {
    private String chatId;
    private String message;
    private List<String> messages;
    private boolean linkPreview;
    private String quotedMessageId;
    private String fileName;
    private String caption;
    private String urlFile;
    private String archive;
    private String nameLocation;
    private String address;
    private String latitude;
    private String longitude;
    private Contact contact;
}
