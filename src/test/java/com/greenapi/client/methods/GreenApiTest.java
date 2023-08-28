package com.greenapi.client.methods;

import com.greenapi.client.GreenApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class GreenApiTest {
    @Value("${green-api.test-data.group-chat-id}")
    protected String groupChatId;
    @Value("${green-api.test-data.group-member-id}")
    protected String groupMemberId;
    @Value("${green-api.test-data.chat-id}")
    protected String chatId;
    @Value("${green-api.test-data.message-id}")
    protected String messageId;
    @Value("${green-api.test-data.file-message-id}")
    protected String fileMessageId;
    @Value("${green-api.test-data.file-url}")
    protected String fileUrl;
    @Autowired
    protected GreenApiClient greenApiClient;
}
