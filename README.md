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

| API method                        | Description                                                                                                               | Documentation link                                                                                         |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| `account.getSettings()`           | The method is designed to get the current settings of the account                                                         | [GetSettings](https://greenapi.com/en/docs/api/account/GetSettings/)                                       |
| `account.setSettings()`           | The method is designed to set the account settings                                                                        | [SetSettings](https://greenapi.com/en/docs/api/account/SetSettings/)                                       |
| `account.getStateInstance()`      | The method is designed to get the state of the account                                                                    | [GetStateInstance](https://greenapi.com/en/docs/api/account/GetStateInstance/)                             |
| `account.getStatusInstance()`     | The method is designed to get the socket connection state of the account instance with WhatsApp                           | [GetStatusInstance](https://greenapi.com/en/docs/api/account/GetStatusInstance/)                           |
| `account.reboot()`                | The method is designed to restart the account                                                                             | [Reboot](https://greenapi.com/en/docs/api/account/Reboot/)                                                 |
| `account.logout()`                | The method is designed to unlogin the account                                                                             | [Logout](https://greenapi.com/en/docs/api/account/Logout/)                                                 |
| `account.qr()`                    | The method is designed to get a QR code                                                                                   | [QR](https://greenapi.com/en/docs/api/account/QR/)                                                         |
| `account.SetProfilePicture()`     | The method is designed to set the avatar of the account                                                                   | [SetProfilePicture](https://greenapi.com/en/docs/api/account/SetProfilePicture/)                           |
| `device.GetDeviceInfo()`          | The method is designed to get information about the device (phone) on which the WhatsApp Business application is running  | [GetDeviceInfo](https://greenapi.com/en/docs/api/phone/GetDeviceInfo/)                                     |
| `groups.CreateGroup()`            | The method is designed to create a group chat                                                                             | [CreateGroup](https://greenapi.com/en/docs/api/groups/CreateGroup/)                                        |
| `groups.UpdateGroupName()`        | The method changes the name of the group chat                                                                             | [UpdateGroupName](https://greenapi.com/en/docs/api/groups/UpdateGroupName/)                                |
| `groups.GetGroupData()`           | The method gets group chat data                                                                                           | [GetGroupData](https://greenapi.com/en/docs/api/groups/GetGroupData/)                                      |
| `groups.AddGroupParticipant()`    | The method adds a participant to the group chat                                                                           | [AddGroupParticipant](https://greenapi.com/en/docs/api/groups/AddGroupParticipant/)                        |
| `groups.RemoveGroupParticipant()` | The method removes the participant from the group chat                                                                    | [RemoveGroupParticipant](https://greenapi.com/en/docs/api/groups/RemoveGroupParticipant/)                  |
| `groups.SetGroupAdmin()`          | The method designates a member of a group chat as an administrator                                                        | [SetGroupAdmin](https://greenapi.com/en/docs/api/groups/SetGroupAdmin/)                                    |
| `groups.RemoveAdmin()`            | The method deprives the participant of group chat administration rights                                                   | [RemoveAdmin](https://greenapi.com/en/docs/api/groups/RemoveAdmin/)                                        |
| `groups.SetGroupPicture()`        | The method sets the avatar of the group                                                                                   | [SetGroupPicture](https://greenapi.com/en/docs/api/groups/SetGroupPicture/)                                |
| `groups.LeaveGroup()`             | The method logs the user of the current account out of the group chat                                                     | [LeaveGroup](https://greenapi.com/en/docs/api/groups/LeaveGroup/)                                          |
| `journals.GetChatHistory()`       | The method returns the chat message history                                                                               | [GetChatHistory](https://greenapi.com/en/docs/api/journals/GetChatHistory/)                                |
| `journals.GetMessage()`           | The method returns a chat message                                                                                         | [GetMessage](https://greenapi.com/en/docs/api/journals/GetMessage/)                                        |
| `journals.LastIncomingMessages()` | The method returns the most recent incoming messages of the account                                                       | [LastIncomingMessages](https://greenapi.com/en/docs/api/journals/LastIncomingMessages/)                    |
| `journals.LastOutgoingMessages()` | The method returns the last sent messages of the account                                                                  | [LastOutgoingMessages](https://greenapi.com/en/docs/api/journals/LastOutgoingMessages/)                    |
| `queues.ShowMessagesQueue()`      | The method is designed to get the list of messages that are in the queue to be sent                                       | [ShowMessagesQueue](https://greenapi.com/en/docs/api/queues/ShowMessagesQueue/)                            |
| `queues.ClearMessagesQueue()`     | The method is designed to clear the queue of messages to be sent                                                          | [ClearMessagesQueue](https://greenapi.com/en/docs/api/queues/ClearMessagesQueue/)                          |
| `readMark.ReadChat()`             | The method is designed to mark chat messages as read                                                                      | [ReadChat](https://greenapi.com/en/docs/api/marks/ReadChat/)                                               |
| `receiving.ReceiveNotification()` | The method is designed to receive a single incoming notification from the notification queue                              | [ReceiveNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/ReceiveNotification/) |
| `receiving.DeleteNotification()`  | The method is designed to remove an incoming notification from the notification queue                                     | [DeleteNotification](https://greenapi.com/en/docs/api/receiving/technology-http-api/DeleteNotification/)   |
| `receiving.DownloadFile()`        | The method is for downloading received and sent files                                                                     | [DownloadFile](https://greenapi.com/en/docs/api/receiving/files/DownloadFile/)                             |
| `sending.SendMessage()`           | The method is designed to send a text message to a personal or group chat                                                 | [SendMessage](https://greenapi.com/en/docs/api/sending/SendMessage/)                                       |
| `sending.SendButtons()`           | The method is designed to send a message with buttons to a personal or group chat                                         | [SendButtons](https://greenapi.com/en/docs/api/sending/SendButtons/)                                       |
| `sending.SendTemplateButtons()`   | The method is designed to send a message with interactive buttons from the list of templates in a personal or group chat  | [SendTemplateButtons](https://greenapi.com/en/docs/api/sending/SendTemplateButtons/)                       |
| `sending.SendPoll()`              | The method is designed to send a Poll in a personal or group chat                                                         | [SendPoll](https://greenapi.com/en/docs/api/sending/SendPoll/)                                             |
| `sending.SendListMessage()`       | The method is designed to send a message with a selection button from a list of values to a personal or group chat        | [SendListMessage](https://greenapi.com/en/docs/api/sending/SendListMessage/)                               |
| `sending.SendFileByUpload()`      | The method is designed to send a file loaded through a form (form-data)                                                   | [SendFileByUpload](https://greenapi.com/en/docs/api/sending/SendFileByUpload/)                             |
| `sending.SendFileByUrl()`         | The method is designed to send a file downloaded via a link                                                               | [SendFileByUrl](https://greenapi.com/en/docs/api/sending/SendFileByUrl/)                                   |
| `sending.UploadFile()`            | The method allows you to upload a file from the local file system, which can later be sent using the SendFileByUrl method | [UploadFile](https://greenapi.com/en/docs/api/sending/UploadFile/)                                         |
| `sending.SendLocation()`          | The method is designed to send a geolocation message                                                                      | [SendLocation](https://greenapi.com/en/docs/api/sending/SendLocation/)                                     |
| `sending.SendContact()`           | The method is for sending a message with a contact                                                                        | [SendContact](https://greenapi.com/en/docs/api/sending/SendContact/)                                       |
| `sending.SendLink()`              | The method is designed to send a message with a link that will add an image preview, title and description                | [SendLink](https://greenapi.com/en/docs/api/sending/SendLink/)                                             |
| `sending.ForwardMessages()`       | The method is designed for forwarding messages to a personal or group chat                                                | [ForwardMessages](https://greenapi.com/en/docs/api/sending/ForwardMessages/)                               |
| `service.CheckWhatsapp()`         | The method checks if there is a WhatsApp account on the phone number                                                      | [CheckWhatsapp](https://greenapi.com/en/docs/api/service/CheckWhatsapp/)                                   |
| `service.GetAvatar()`             | The method returns the avatar of the correspondent or group chat                                                          | [GetAvatar](https://greenapi.com/en/docs/api/service/GetAvatar/)                                           |
| `service.GetContacts()`           | The method is designed to get a list of contacts of the current account                                                   | [GetContacts](https://greenapi.com/en/docs/api/service/GetContacts/)                                       |
| `service.GetContactInfo()`        | The method is designed to obtain information about the contact                                                            | [GetContactInfo](https://greenapi.com/en/docs/api/service/GetContactInfo/)                                 |
| `service.DeleteMessage()`         | The method deletes the message from chat                                                                                  | [DeleteMessage](https://greenapi.com/en/docs/api/service/deleteMessage/)                                   |
| `service.ArchiveChat()`           | The method archives the chat                                                                                              | [ArchiveChat](https://greenapi.com/en/docs/api/service/archiveChat/)                                       |
| `service.UnarchiveChat()`         | The method unarchives the chat                                                                                            | [UnarchiveChat](https://greenapi.com/en/docs/api/service/unarchiveChat/)                                   |
| `service.SetDisappearingChat()`   | The method is designed to change the settings of disappearing messages in chats                                           | [SetDisappearingChat](https://greenapi.com/en/docs/api/service/SetDisappearingChat/)                       |
| `webhook.Start()`                 | The method is designed to start receiving new notifications                                                               |                                                                                                            |
| `webhook.Stop()`                  | The method is designed to stop receiving new notifications                                                                |                                                                                                            |

## Service methods documentation

[Service methods documentation](https://greenapi.com/en/docs/api/)

## License

Licensed under [
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
](https://creativecommons.org/licenses/by-nd/4.0/) terms.
Please see file [LICENSE](https://github.com/green-api/whatsapp-api-client-java/blob/master/LICENSE.txt).