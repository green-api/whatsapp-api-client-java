# whatsapp-api-client-java

whatsapp-api-client-java is a library for integration with WhatsApp messenger using the API
service [green-api.com](https://green-api.com/en/). You should get a registration token and an account ID in
your [personal cabinet](https://console.green-api.com/) to use the library. There is a free developer account tariff.

## API

The documentation for the REST API can be found at the [link](https://greenapi.com/en/docs/). The library is a wrapper
for the REST API, so the documentation at the link above also applies.

#### Authorization

To send a message or perform other Green API methods, the WhatsApp account in the phone app must be authorized. To
authorize the account, go to your [cabinet](https://console.green-api.com/) and scan the QR code using the WhatsApp app.

## Installation

Do not forget to create a module:

1. Download the JAR file.
2. Include the JAR file in your project manually by adding it to your libraries folder (usually lib or libs)
   and configure your development environment (eg IntelliJ IDEA) to include this JAR file in the project path:

   - Open your project in IntelliJ IDEA.
   - Go to menu "File" -> "Project Structure".
   - In the "Modules" section, select your module.
   - Go to the "Dependencies" tab.
   - Click on the "+" button and select "JARs or directories".
   - Select the downloaded JAR file and add it.

3. Now you can use classes and functions from the library in your project code. Import the appropriate packages and start using the library.

If you use Gradle, you can build project after add a JAR file in your project folder:

```
dependencies {
    implementation files('libs/greenapi-java-examples.jar')
}
```

## Examples

### How to initialize an object

To get started, you will need to create an object of the GreenApiClient class.
Since you are using Spring, you can do this in three ways:

1. Use a ready-made bean that the client builds according to the parameters in the yml file. To do this, specify the parameters of your instance in application.yml in the following form:

```
green-api:
   host: https://api.green-api.com
   hostMedia: https://media.green-api.com
   instanceId: {{yourInstance}}
   token: {{yourToken}}
```

Create RestTemplate bean with your configuration, for example:

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
return restTemplateBuilder.build();
}
```

And add "com.greenapi" in @ComponentScan base packages:

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.greenapi", "com.example"})
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
    }
}
```

Now you can inject the WhatsApp client instance into any part of your application.

```java
@Autowired
private GreenApiClient greenApiClient;
```

2. Another way to use yours configuration, for example:

```java
@Configuration
public class GreenApiConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public GreenApiClient greenApiClient(RestTemplate restTemplate) {
        return new GreenApiClient(
            restTemplate,
            "https://media.greenapi.com",
            "https://api.greenapi.com",
            "{{YOUR-ID}}",
            "{{YOUR-TOKEN}}");
    }
}
```

3. Use a constructor to create a new instances, if your application simultaneously manages several of it:

```java
var restTemplate = new RestTemplateBuilder().build();

var greenApiClient1 = new GreenApiClient(
    restTemplate,
    "https://media.green-api.com",
    "https://api.green-api.com",
    {{instanceId1}},
    {{instanceToken1}});

var greenApiClient2 = new GreenApiClient(
    restTemplate,
    "https://media.greenapi.com",
    "https://api.greenapi.com",
    {{instanceId2}},
    {{instanceToken2}});
```
### How to create a group and send message

Link to example: [CreateGroupSendMessageExample.java](src/main/java/com/greenapi/examples/CreateGroupSendMessageExample.java).

```java
@Log4j2
class CreateGroupSendMessageExample {

   private void createGroupAndSendMessage(GreenApi greenApi) {
      var groupMembers = new ArrayList<String>();
      groupMembers.add("11001234567@c.us");
      groupMembers.add("11001234566@c.us");
      groupMembers.add("11001234565@c.us");

      var group = greenApi.groups.createGroup(
                      CreateGroupReq.builder()
                              .groupName("Test Group")
                              .chatIds(groupMembers)
                              .build()).getBody();

      if (group != null) {
         var message = greenApi.sending.sendMessage(
                         OutgoingMessage.builder()
                                 .chatId(group.getChatId())
                                 .message("hola a todos")
                                 .build()).getBody();

         if (message != null) {
            log.info("Create group: " + group.isCreated() +
                    "\nSend message: " + message.getIdMessage());
         }
      }
   }
}
```

### How to send a file by uploading from the disk

To send a file, you need to give the path to the file.

Link to example: [SendFileByUploadExample.java](src/main/java/com/greenapi/examples/SendFileByUploadExample.java).

```java
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
```

### How to send a file by URL

Link to example: [SendFileByUrlExample.java](src/main/java/com/greenapi/examples/SendFileByUrlExample.java).

```java
@Log4j2
public class SendFileByUrlExample {

    private void sendFileByUrlExample(GreenApi greenApi) {
        var response = greenApi.sending.sendFileByUrl(OutgoingFileByUrl.builder()
            .urlFile("https://go.dev/blog/go-brand/Go-Logo/SVG/Go-Logo_Blue.svg")
            .fileName("Go-Logo_Blue.svg")
            .chatId("11001234567@c.us")
            .build());

        if (response.getStatusCode().isError()) {
            log.warn("message sending is failed");
        }

        log.info("message sent, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
    }
}
```

### How to receive incoming notifications

To receive incoming webhooks, you must send a handler function to `Webhook().Start`. The handler function should have
one parameter (`body map[string]interface{}`). When you receive a new notification, your handler function will be
executed. To stop receiving incoming webhooks, you need to call `Webhook().Stop`.

Link to example: [WebhookExample.java](src/main/java/com/greenapi/WebhookExample.java).

```java
@SpringBootApplication
public class WebhookExample {
   public static void main(String[] args) {
      var context = SpringApplication.run(WebhookExample.class, args);

      var webhookConsumer = (WebhookConsumer) context.getBean("webhookConsumer");
      webhookConsumer.start(notification -> System.out.println("New webhook received: " + notification));
   }
}
```

## List of examples

| Description                                   | Link to example                                                                            |
|-----------------------------------------------|--------------------------------------------------------------------------------------------|
| How to create a group  and send message       | [CreateGroupSendMessageExample.java](src/main/java/com/greenapi/examples/CreateGroupSendMessageExample.java) |
| How to send a file by uploading from the disk | [SendFileByUploadExample.java](src/main/java/com/greenapi/examples/SendFileByUploadExample.java)             |
| How to send a file by URL                     | [SendFileByUrlExample.java](src/main/java/com/greenapi/examples/SendFileByUrlExample.java) |
| How to receive incoming notifications         | [WebhookExample.java](src/main/java/com/greenapi/WebhookExample.java)                      |

## List of all library methods

| API method                        | Description                                                                                                               | Documentation link                                                     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| `Account().GetSettings`           | The method is designed to get the current settings of the account                                                         | [GetSettings](https://greenapi.com/en/docs/api/account/GetSettings/)   |
| `Account().SetSettings`           | The method is designed to set the account settings                                                                        | [SetSettings](https://greenapi.com/en/docs/api/account/SetSettings/)   |
| `Account().GetStateInstance`      | The method is designed to get the state of the account                                                                    | [GetStateInstance](https://greenapi.com/en/docs/api/account/GetStateInstance/) |
| `Account().GetStatusInstance`     | The method is designed to get the socket connection state of the account instance with WhatsApp                           | [GetStatusInstance](https://greenapi.com/en/docs/api/account/GetStatusInstance/) |
| `Account().Reboot`                | The method is designed to restart the account                                                                             | [Reboot](https://greenapi.com/en/docs/api/account/Reboot/)             |
| `Account().Logout`                | The method is designed to unlogin the account                                                                             | [Logout](https://greenapi.com/en/docs/api/account/Logout/)             |
| `Account().QR`                    | The method is designed to get a QR code                                                                                   | [QR](https://greenapi.com/en/docs/api/account/QR/)                     |
| `Account().SetProfilePicture`     | The method is designed to set the avatar of the account                                                                   | [SetProfilePicture](https://greenapi.com/en/docs/api/account/SetProfilePicture/) |
| `Device().GetDeviceInfo`          | The method is designed to get information about the device (phone) on which the WhatsApp Business application is running  | [GetDeviceInfo](https://greenapi.com/en/docs/api/phone/GetDeviceInfo/) |
| `Groups().CreateGroup`            | The method is designed to create a group chat                                                                             | [CreateGroup](https://greenapi.com/en/docs/api/groups/CreateGroup/)    |
| `Groups().UpdateGroupName`        | The method changes the name of the group chat                                                                             | [UpdateGroupName](https://greenapi.com/en/docs/api/groups/UpdateGroupName/) |
| `Groups().GetGroupData`           | The method gets group chat data                                                                                           | [GetGroupData](https://greenapi.com/en/docs/api/groups/GetGroupData/)  |
| `Groups().AddGroupParticipant`    | The method adds a participant to the group chat                                                                           | [AddGroupParticipant](https://greenapi.com/en/docs/api/groups/AddGroupParticipant/) |
| `Groups().RemoveGroupParticipant` | The method removes the participant from the group chat                                                                    | [RemoveGroupParticipant](https://greenapi.com/en/docs/api/groups/RemoveGroupParticipant/) |
| `Groups().SetGroupAdmin`          | The method designates a member of a group chat as an administrator                                                        | [SetGroupAdmin](https://greenapi.com/en/docs/api/groups/SetGroupAdmin/) |
| `Groups().RemoveAdmin`            | The method deprives the participant of group chat administration rights                                                   | [RemoveAdmin](https://greenapi.com/en/docs/api/groups/RemoveAdmin/)    |
| `Groups().SetGroupPicture`        | The method sets the avatar of the group                                                                                   | [SetGroupPicture](https://greenapi.com/en/docs/api/groups/SetGroupPicture/) |
| `Groups().LeaveGroup`             | The method logs the user of the current account out of the group chat                                                     | [LeaveGroup](https://greenapi.com/en/docs/api/groups/LeaveGroup/)      |
| `Journals().GetChatHistory`       | The method returns the chat message history                                                                               | [GetChatHistory](https://greenapi.com/en/docs/api/journals/GetChatHistory/) |
| `Journals().GetMessage`           | The method returns a chat message                                                                                         | [GetMessage](https://greenapi.com/en/docs/api/journals/GetMessage/)    |
| `Journals().LastIncomingMessages` | The method returns the most recent incoming messages of the account                                                       | [LastIncomingMessages](https://greenapi.com/en/docs/api/journals/LastIncomingMessages/) |
| `Journals().LastOutgoingMessages` | The method returns the last sent messages of the account                                                                  | [LastOutgoingMessages](https://greenapi.com/en/docs/api/journals/LastOutgoingMessages/) |
| `Queues().ShowMessagesQueue`      | The method is designed to get the list of messages that are in the queue to be sent                                       | [ShowMessagesQueue](https://greenapi.com/en/docs/api/queues/ShowMessagesQueue/) |
| `Queues().ClearMessagesQueue`     | The method is designed to clear the queue of messages to be sent                                                          | [ClearMessagesQueue](https://greenapi.com/en/docs/api/queues/ClearMessagesQueue/) |
| `ReadMark().ReadChat`             | The method is designed to mark chat messages as read                                                                      | [ReadChat](https://greenapi.com/en/docs/api/marks/ReadChat/)           |
| `Receiving().ReceiveNotification` | The method is designed to receive a single incoming notification from the notification queue                              | [ReceiveNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/ReceiveNotification/) |
| `Receiving().DeleteNotification`  | The method is designed to remove an incoming notification from the notification queue                                     | [DeleteNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/DeleteNotification/) |
| `Receiving().DownloadFile`        | The method is for downloading received and sent files                                                                     | [DownloadFile](https://greenapi.com/en/docs/api/receiving/files/DownloadFile/) |
| `Sending().SendMessage`           | The method is designed to send a text message to a personal or group chat                                                 | [SendMessage](https://greenapi.com/en/docs/api/sending/SendMessage/)   |
| `Sending().SendButtons`           | The method is designed to send a message with buttons to a personal or group chat                                         | [SendButtons](https://greenapi.com/en/docs/api/sending/SendButtons/)   |
| `Sending().SendTemplateButtons`   | The method is designed to send a message with interactive buttons from the list of templates in a personal or group chat  | [SendTemplateButtons](https://greenapi.com/en/docs/api/sending/SendTemplateButtons/) |
| `Sending().SendListMessage`       | The method is designed to send a message with a selection button from a list of values to a personal or group chat        | [SendListMessage](https://greenapi.com/en/docs/api/sending/SendListMessage/) |
| `Sending().SendFileByUpload`      | The method is designed to send a file loaded through a form (form-data)                                                   | [SendFileByUpload](https://greenapi.com/en/docs/api/sending/SendFileByUpload/) |
| `Sending().SendFileByUrl`         | The method is designed to send a file downloaded via a link                                                               | [SendFileByUrl](https://greenapi.com/en/docs/api/sending/SendFileByUrl/) |
| `Sending().UploadFile`            | The method allows you to upload a file from the local file system, which can later be sent using the SendFileByUrl method | [UploadFile](https://greenapi.com/en/docs/api/sending/UploadFile/)     |
| `Sending().SendLocation`          | The method is designed to send a geolocation message                                                                      | [SendLocation](https://greenapi.com/en/docs/api/sending/SendLocation/) |
| `Sending().SendContact`           | The method is for sending a message with a contact                                                                        | [SendContact](https://greenapi.com/en/docs/api/sending/SendContact/)   |
| `Sending().SendLink`              | The method is designed to send a message with a link that will add an image preview, title and description                | [SendLink](https://greenapi.com/en/docs/api/sending/SendLink/)         |
| `Sending().ForwardMessages`       | The method is designed for forwarding messages to a personal or group chat                                                | [ForwardMessages](https://greenapi.com/en/docs/api/sending/ForwardMessages/) |
| `Service().CheckWhatsapp`         | The method checks if there is a WhatsApp account on the phone number                                                      | [CheckWhatsapp](https://greenapi.com/en/docs/api/service/CheckWhatsapp/) |
| `Service().GetAvatar`             | The method returns the avatar of the correspondent or group chat                                                          | [GetAvatar](https://greenapi.com/en/docs/api/service/GetAvatar/)       |
| `Service().GetContacts`           | The method is designed to get a list of contacts of the current account                                                   | [GetContacts](https://greenapi.com/en/docs/api/service/GetContacts/)   |
| `Service().GetContactInfo`        | The method is designed to obtain information about the contact                                                            | [GetContactInfo](https://greenapi.com/en/docs/api/service/GetContactInfo/) |
| `Service().DeleteMessage`         | The method deletes the message from chat                                                                                  | [DeleteMessage](https://greenapi.com/en/docs/api/service/deleteMessage/) |
| `Service().ArchiveChat`           | The method archives the chat                                                                                              | [ArchiveChat](https://greenapi.com/en/docs/api/service/archiveChat/)   |
| `Service().UnarchiveChat`         | The method unarchives the chat                                                                                            | [UnarchiveChat](https://greenapi.com/en/docs/api/service/unarchiveChat/) |
| `Service().SetDisappearingChat`   | The method is designed to change the settings of disappearing messages in chats                                           | [SetDisappearingChat](https://greenapi.com/en/docs/api/service/SetDisappearingChat/) |
| `Webhook().Start`                 | The method is designed to start receiving new notifications                                                               |                                                                        |
| `Webhook().Stop`                  | The method is designed to stop receiving new notifications                                                                |                                                                        |

## Service methods documentation

[Service methods documentation](https://greenapi.com/en/docs/api/)

## License

Licensed under [
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
](https://creativecommons.org/licenses/by-nd/4.0/) terms.
Please see file [LICENSE](LICENSE).