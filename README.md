# whatsapp-api-client-java

[Ссылка русскоязычную инструкцию](src/main/java/com/greenapi/client/docs/README_RU.md)

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

Maven
```
<dependency>
  <groupId>com.green-api</groupId>
  <artifactId>whatsapp-api-client-java</artifactId>
  <version>version</version>
</dependency>
```

Gradle
```
implementation group: 'com.green-api', name: 'whatsapp-api-client-java', version: 'version'
```

## Examples

### How to initialize an object

You can configure your bean, use application.yml, or instantiate class via the constructor.

**Via configuration:**

```java
@Configuration
public class GreenApiConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public GreenApi greenApi(RestTemplate restTemplate) {
        return new GreenApi(
            restTemplate,
            "https://media.greenapi.com",
            "https://api.greenapi.com",
            "{{YOUR-ID}}",
            "{{YOUR-TOKEN}}");
    }
}
```

**Via application.yml:**

To use a ready-made bean that is created based on application.yml parameters, specify the parameters of your
instance in the application.yml file as follows:

```yaml
green-api:
   host: https://api.green-api.com
   hostMedia: https://media.green-api.com
   instanceId: {{yourInstance}}
   token: {{yourToken}}
```

Make sure you have a `RestTemplate` bean with your configuration, like this:

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
    return restTemplateBuilder.build();
    }
```

And add `com.greenapi.client` to the base scanning packages using the `@ComponentScan` annotation:

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.greenapi.client", "com.example"})
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
    }
}
```

**Via constructor:**

```java
var restTemplate = new RestTemplateBuilder().build();

    var greenApi1 = new GreenApi(
    restTemplate,
    "https://media.green-api.com",
    "https://api.green-api.com",
    {{instanceId1}},
    {{instanceToken1}});

    var greenApi2 = new GreenApi(
    restTemplate,
    "https://media.greenapi.com",
    "https://api.greenapi.com",
    {{instanceId2}},
    {{instanceToken2}});
```

### How to send message

Link to example: [sendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/sendMessageExample.java).

```java
@Log4j2
public class SendMessageExample {
    private void sendMessageExample(GreenApi greenApi) {
        var message = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("111111111111@c.us")
                .message("hola a todos")
                .build());

        if (message.getStatusCode().is2xxSuccessful()) {
            log.info(message.getBody());
        } else {
            log.warn("Message isn't sent, status code: " + message.getStatusCode());
        }
    }
}
```

### How to create a group and send message

Link to example: [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java).

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

Link to example: [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java).

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

Link to example: [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java).

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

### How to send a file by UploadFile + SendFileByUrl

Link to example: [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java).

```java
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
```

### How to send a Poll

Link to example: [SendPollExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendPollExample.java).

```java
@Log4j2
public class SendPollExample {
    private void sendPollExample(GreenApi greenApi) {
        var options = new ArrayList<Option>();
        options.add(new Option("option 1"));
        options.add(new Option("option 2"));
        options.add(new Option("option 3"));

        var dto = OutgoingPoll.builder()
            .chatId("111111111111@c.us")
            .message("text message")
            .options(options)
            .multipleAnswers(false)
            .build();

        var response = greenApi.sending.sendPoll(dto);
        log.info(response);
    }
}
```

### How to receive incoming notifications

To start receiving notifications, you need to pass a handler function to `webhookConsumer.start()`. 
The handler function should implement the `WebhookHandler` interface. 
When a new notification is received, your handler function will be executed. 
To stop receiving notifications, you need to call the `webhookConsumer.stop(`) function.

`WebhookConsumer` is a class responsible for processing messages. For its correct functioning, it requires `GreenApi` and `NotificationMapper`. 
You can inject them into it using beans or through the constructor.

`NotificationMapper` is a bean responsible for converting a JSON object into a Java object. 
It uses the `ObjectMapper` from the `com.fasterxml.jackson` library, which should be available as a bean in your configuration or set via the constructor.

`WebhookHandler` is an interface. You can write any class to handle notifications; just implement the interface 
and execute your logic in the `handle()` method or use a lambda expression.

```java
public interface WebhookHandler {
    void handle(Notification notification);
}
```

Link to example: [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java).

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

Since each notification is automatically cast to a java object, you can filter the notification by any field yourself.
A description of the structure of notification objects can be found at this link: [Documentation](https://green-api.com/docs/api/receiving/notifications-format/type-webhook/)
For convenience, all types of hooks and messages are named similarly to the documentation:

| Java object                           | Webhook's json object                                                                                                                                       |
|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `TextMessageWebhook`                  | [TextMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/TextMessage/)                                                |
| `TemplateMessageWebhook`              | [TemplateMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/TemplateMessage/)                                        |
| `StickerMessageWebhook`               | [StickerMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/StickerMessage/)                                          |
| `ReactionMessageWebhook`              | [ReactionMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ReactionMessage/)                                        |
| `QuotedMessageWebhook`                | [QuotedMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/QuotedMessage/)                                            |
| `PollUpdateMessageWebhook`            | [PollUpdateMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/PollUpdateMessage/)                                    |
| `PollMessageWebhook`                  | [PollMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/PollMessage/)                                                |
| `LocationMessageWebhook`              | [LocationMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/LocationMessage/)                                        |
| `ListMessageWebhook`                  | [ListMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ListMessage/)                                                |
| `GroupInviteMessageWebhook`           | [GroupInviteMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/GroupInviteMessage/)                                  |
| `FileMessageWebhook`                  | [imageMessage, videoMessage, documentMessage, audioMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ImageMessage/) |
| `ExtendedTextMessageWebhook`          | [ExtendedTextMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ExtendedTextMessage/)                                |
| `ButtonsMessageWebhook`               | [ButtonsMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ButtonsMessage/)                                          |
| `ContactMessageWebhook`               | [ContactMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ContactMessage/)                                          |
| `ContactsArrayMessageWebhook`         | [ContactMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/incoming-message/ContactsArrayMessage/)                                    |
| `TemplateButtonsReplyMessageWebhook`  | [TemplateButtonsReplyMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/selected-buttons/TemplateButtonsReplyMessage/)                |
| `ButtonsResponseMessageWebhook`       | [ButtonsResponseMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/selected-buttons/ButtonsResponseMessage/)                          |
| `ListResponseMessageWebhook`          | [ListResponseMessage](https://greenapi.com/en/docs/api/receiving/notifications-format/selected-buttons/ListResponseMessage/)                                |

## List of examples

| Description                                   | Link to example                                                                                                                                                                       |
|-----------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| How to send message                           | [SendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendMessageExample.java)                       |
| How to create a group  and send message       | [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java) |
| How to send a file by uploading from the disk | [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java)             |
| How to send a file by URL                     | [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java)                   |
| How to send a file by UploadFile + SendByUrl  | [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java) |
| How to receive incoming notifications         | [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java)                               |

## List of all library methods

| Method                                 | Description                                                                                                                                                  | Documentation                                                                                                               |
|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| `account.getSettings()`                | The method is aimed for getting the current settings of the account settings                                                                                 | [GetSettings](https://greenapi.com/en/docs/api/account/GetSettings)                                      |
| `account.setSettings()`                | The method is aimed for setting an account settings settings                                                                                                 | [SetSettings](https://greenapi.com/en/docs/api/account/SetSettings)                                      |
| `account.getStateInstance()`           | The method is aimed for getting the account state                                                                                                            | [GetStateInstance](https://greenapi.com/en/docs/api/account/GetStateInstance)                            |
| `account.getStatusInstance()`          | The method is aimed for getting the status of the account instance socket connection with WhatsApp                                                           | [GetStatusInstance](https://greenapi.com/en/docs/api/account/GetStatusInstance)                          |
| `account.reboot()`                     | The method is aimed for rebooting an account                                                                                                                 | [Reboot](https://greenapi.com/en/docs/api/account/Reboot)                                                |
| `account.logout()`                     | The method is aimed for logging out an account                                                                                                               | [Logout](https://greenapi.com/en/docs/api/account/Logout)                                                |
| `account.qr()`                         | The method is aimed for getting QR code                                                                                                                      | [QR](https://greenapi.com/en/docs/api/account/QR)                                                        |
| `account.getAuthorizationCode()`       | The method is intended to authorize an instance by phone number. The method is used as an alternative to the QR method.                                      | [GetAuthorizationCode](https://greenapi.com/en/docs/api/account/GetAuthorizationCode)                    |
| `account.setProfilePicture()`          | The method is aimed for setting an account picture                                                                                                           | [SetProfilePicture](https://greenapi.com/en/docs/api/account/SetProfilePicture)                          |
| `device.getDeviceInfo()`               | The method is aimed for getting information about the device (phone) running WhatsApp Business application                                                   | [GetDeviceInfo](https://greenapi.com/en/docs/api/phone/GetDeviceInfo)                                    |
| `groups.createGroup()`                 | The method adds a participant to a group chat. IMPORTANT: If one tries to create a group with a non-existent number, WhatsApp may block the sender's number. | [CreateGroup](https://greenapi.com/en/docs/api/groups/CreateGroup)                                       |
| `groups.updateGroupName()`             | The method changes a group chat name                                                                                                                         | [UpdateGroupName](https://greenapi.com/en/docs/api/groups/UpdateGroupName)                               |
| `groups.getGroupData()`                | The method gets group chat data                                                                                                                              | [GetGroupData](https://greenapi.com/en/docs/api/groups/GetGroupData)                                     |
| `groups.addGroupParticipant()`         | The method adds a participant to a group chat                                                                                                                | [AddGroupParticipant](https://greenapi.com/en/docs/api/groups/AddGroupParticipant)                       |
| `groups.removeGroupParticipant()`      | The method removes a participant from a group chat                                                                                                           | [RemoveGroupParticipant](https://greenapi.com/en/docs/api/groups/RemoveGroupParticipant)                 |
| `groups.setGroupAdmin()`               | The method sets a group chat participant as an administrator                                                                                                 | [SetGroupAdmin](https://greenapi.com/en/docs/api/groups/SetGroupAdmin)                                   |
| `groups.removeAdmin()`                 | The method removes a participant from group chat administartion rights                                                                                       | [RemoveAdmin](https://greenapi.com/en/docs/api/groups/RemoveAdmin)                                       |
| `groups.setGroupPicture()`             | The method sets a group picture                                                                                                                              | [SetGroupPicture](https://greenapi.com/en/docs/api/groups/SetGroupPicture)                               |
| `groups.leaveGroup()`                  | The method makes the current account user leave the group chat                                                                                               | [LeaveGroup](https://greenapi.com/en/docs/api/groups/LeaveGroup)                                         |
| `journals.getChatHistory()`            | The method returns the chat message history                                                                                                                  | [GetChatHistory](https://greenapi.com/en/docs/api/journals/GetChatHistory)                               |
| `journals.getMessage()`                | The method returns the chat message                                                                                                                          | [GetMessage](https://greenapi.com/en/docs/api/journals/GetMessage)                                       |
| `journals.lastIncomingMessages()`      | The method returns the last incoming messages of the account. In the default mode the incoming messages for 24 hours are returned                            | [LastIncomingMessages](https://greenapi.com/en/docs/api/journals/LastIncomingMessages)                   |
| `journals.lastOutgoingMessages()`      | The method returns the last outgoing messages of the account. In the default mode the last messages for 24 hours are returned                                | [LastOutgoingMessages](https://greenapi.com/en/docs/api/journals/LastOutgoingMessages)                   |
| `queues.showMessagesQueue()`           | The method is aimed for getting a list of messages in the queue to be sent                                                                                   | [ShowMessagesQueue](https://greenapi.com/en/docs/api/queues/ShowMessagesQueue)                           |
| `queues.slearMessagesQueue()`          | The method is aimed for clearing the queue of messages to be sent                                                                                            | [ClearMessagesQueue](https://greenapi.com/en/docs/api/queues/ClearMessagesQueue)                         |
| `readMark.readChat()`                  | The method is aimed for marking messages in a chat as read. Either all messages or a specified message in a chat can be marked as read                       | [ReadChat](https://greenapi.com/en/docs/api/marks/ReadChat)                                              |
| `receiving.receiveNotification()`      | The method is aimed for receiving one incoming notification from the notifications queue                                                                     | [ReceiveNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/ReceiveNotification)|
| `receiving.deleteNotification()`       | The method is aimed for deleting an incoming notification from the notification queue                                                                        | [DeleteNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/DeleteNotification)  |
| `receiving.downloadFile()`             | The method is aimed for downloading incoming and outgoing files                                                                                              | [DownloadFile](https://greenapi.com/en/docs/api/receiving/files/DownloadFile)                            |
| `sending.sendMessage()`                | The method is aimed for sending a text message to a personal or a group chat                                                                                 | [SendMessage](https://greenapi.com/en/docs/api/sending/SendMessage)                                      |
| `sending.sendButtons()`                | The method is aimed for sending a button message to a personal or a group chat                                                                               | [SendButtons](https://greenapi.com/en/docs/api/sending/SendButtons)                                      |
| `sending.sendTemplateButtons()`        | The method is aimed for sending a message with template list interacrive buttons to a personal or a group chat                                               | [SendTemplateButtons](https://greenapi.com/en/docs/api/sending/SendTemplateButtons)                      |
| `sending.sendPoll()`                   | The method is aimed for sending a poll a personal or a group chat                                                                                            | [SendPoll](https://greenapi.com/en/docs/api/sending/SendPoll)                                            |
| `sending.sendListMessage()`            | The method is aimed for sending a message with a select button from a list of values to a personal or a group chat                                           | [SendListMessage](https://greenapi.com/en/docs/api/sending/SendListMessage)                              |
| `sending.sendFileByUpload()`           | The method is aimed for sending a file uploaded by form (form-data)                                                                                          | [SendFileByUpload](https://greenapi.com/en/docs/api/sending/SendFileByUpload)                            |
| `sending.sendFileByUrl()`              | The method is aimed for sending a file uploaded by Url                                                                                                       | [SendFileByUrl](https://greenapi.com/en/docs/api/sending/SendFileByUrl)                                  |
| `sending.uploadFile()`                 | The method is designed to upload a file to the cloud storage, which can be sent using the sendFileByUrl method                                               | [UploadFile](https://greenapi.com/en/docs/api/sending/UploadFile)                                        |
| `sending.sendLocation()`               | The method is aimed for sending a location message                                                                                                           | [SendLocation](https://greenapi.com/en/docs/api/sending/SendLocation)                                    |
| `sending.sendContact()`                | The method is aimed for sending a contact message                                                                                                            | [SendContact](https://greenapi.com/en/docs/api/sending/SendContact)                                      |
| `sending.sendLink()`                   | The method is aimed for sending a message with a link, by which an image preview, title and description will be added                                        | [SendLink](https://greenapi.com/en/docs/api/sending/SendLink)                                            |
| `sending.forwardMessages()`            | The method is intended for forwarding messages to a personal or group chat                                                                                   | [ForwardMessages](https://greenapi.com/en/docs/api/sending/ForwardMessages)                              |
| `service.checkWhatsapp()`              | The method checks WhatsApp account availability on a phone number                                                                                            | [CheckWhatsapp](https://greenapi.com/en/docs/api/service/CheckWhatsapp)                                  |
| `service.getAvatar()`                  | The method returns a user or a group chat avatar                                                                                                             | [GetAvatar](https://greenapi.com/en/docs/api/service/GetAvatar)                                          |
| `service.getContacts()`                | The method is aimed for getting a list of the current account contacts                                                                                       | [GetContacts](https://greenapi.com/en/docs/api/service/GetContacts)                                      |
| `service.getContactInfo()`             | The method is aimed for getting information on a contact                                                                                                     | [GetContactInfo](https://greenapi.com/en/docs/api/service/GetContactInfo)                                |
| `service.deleteMessage()`              | The method deletes a message from a chat                                                                                                                     | [DeleteMessage](https://greenapi.com/en/docs/api/service/deleteMessage)                                  |
| `service.archiveChat()`                | The method archives a chat. One can archive chats that have at least one incoming message                                                                    | [ArchiveChat](https://greenapi.com/en/docs/api/service/archiveChat)                                      |
| `service.unarchiveChat()`              | The method unarchives a chat                                                                                                                                 | [UnarchiveChat](https://greenapi.com/en/docs/api/service/unarchiveChat)                                  |
| `service.setDisappearingChat()`        | The method is aimed for changing settings of disappearing messages in chats                                                                                  | [SetDisappearingChat](https://greenapi.com/en/docs/api/service/SetDisappearingChat)                      |
| `webhook.start()`                      | The method is aimed for starting to receive webhooks                                                                                                         |                                                                                                                             |
| `webhook.stop()`                       | The method is aimed for stopping to receive webhooks                                                                                                         |                                                                                                                             |
| `statuses.sendTextStatus()`              | The method is used to send a text status                                                                            | [SendTextStatus](https://greenapi.com/en/docs/api/statuses/SendTextStatus/)                               |
| `statuses.sendVoiceStatus()`             | The method is used to send a voice status                                                                           | [SendVoiceStatus](https://greenapi.com/en/docs/api/statuses/SendVoiceStatus/)                             |
| `statuses.sendMediaStatus()`             | The method is used to send a pictures or video status                                                               | [SendMediaStatus](https://greenapi.com/en/docs/api/statuses/SendMediaStatus/)                             |
| `statuses.getIncomingStatuses()`         | The method is used to get the incoming status messages of the instance                                                          | [GetIncomingStatuses](https://greenapi.com/en/docs/api/statuses/GetIncomingStatuses/)                     |
| `statuses.getOutgoingStatuses()`         | The method is used to get the outgoing statuses of the account                                                                  | [GetOutgoingStatuses](https://greenapi.com/en/docs/api/statuses/GetOutgoingStatuses/)                     |
| `statuses.getStatusStatistic()`          | The method is used to get an array of recipients marked sent/delivered/read for a given status                                  | [GetStatusStatistic](https://greenapi.com/en/docs/api/statuses/GetStatusStatistic/)                       |

## Service methods documentation

[Service methods documentation](https://greenapi.com/en/docs/api/)

## License

Licensed under [
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
](https://creativecommons.org/licenses/by-nd/4.0/) terms.
Please see file [LICENSE](https://github.com/green-api/whatsapp-api-client-java/blob/master/LICENSE.txt).