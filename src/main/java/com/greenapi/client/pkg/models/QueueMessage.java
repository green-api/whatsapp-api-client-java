package com.greenapi.client.pkg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueMessage {
    private String messageID;
    private List<String> messagesIDs;
    private String type;
    private QueueMessageBody body;
}
