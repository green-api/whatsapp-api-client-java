//import com.greenapi.client.pkg.api.GreenApi;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootTest(classes = GreenApiTest.class)
//public class GreenApiTest {
//
//    @Value("${green-api.hostMedia}")
//    protected String hostMedia;
//    @Value("${green-api.host}")
//    protected String host;
//    @Value("${green-api.instanceId}")
//    protected String instanceId;
//    @Value("${green-api.token}")
//    protected String instanceToken;
//    @Value("${green-api.test-data.group-chat-id}")
//    protected String groupChatId;
//    @Value("${green-api.test-data.group-member-id}")
//    protected String groupMemberId;
//    @Value("${green-api.test-data.chat-id}")
//    protected String chatId;
//    @Value("${green-api.test-data.message-id}")
//    protected String messageId;
//    @Value("${green-api.test-data.file-message-id}")
//    protected String fileMessageId;
//    @Value("${green-api.test-data.file-url}")
//    protected String fileUrl;
//    protected GreenApi greenApi;
//
//    @BeforeEach
//    public void init() {
//        greenApi = new GreenApi(new RestTemplate(), hostMedia, host, instanceId, instanceToken);
//    }
//}
