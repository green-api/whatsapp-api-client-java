# whatsapp-api-client-java

whatsapp-api-client-java - библиотека для интеграции с мессенджером WhatsApp через API
сервиса [green-api.com](https://green-api.com/). Чтобы воспользоваться библиотекой, нужно получить регистрационный токен
и ID аккаунта в [личном кабинете](https://console.green-api.com/). Есть бесплатный тариф аккаунта разработчика.

## API

Документация к REST API находится по [ссылке](https://green-api.com/docs/api/). Библиотека является оберткой к REST API,
поэтому документация по ссылке выше применима и к самой библиотеке.

## Авторизация

Чтобы отправить сообщение или выполнить другие методы Green API, аккаунт WhatsApp в приложении телефона должен быть в
авторизованном состоянии. Для авторизации аккаунта перейдите в [личный кабинет](https://console.green-api.com/) и
сканируйте QR-код с использованием приложения WhatsApp.

## Установка

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

## Примеры

### Как инициализировать объект

Вы можете сконфигурировать свой bean, воспользоваться application.property или создать инстанс класса через конструктор.

**Через конфигурацию:**

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

**Через application.yml:**

Или используйте готовый бин, который клиент создается на основе параметров application.yml. Для этого укажите параметры
вашего
экземпляра в файле application.yml следующим образом:

```yaml
green-api:
  host: https://api.green-api.com
  hostMedia: https://media.green-api.com
  instanceId: {{yourInstance}}
  token: {{yourToken}}
```

Убедитесь, что у вас есть `RestTemplate` bean с вашей конфигурацией, например:

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
    return restTemplateBuilder.build();
    }
```

И добавьте `com.greenapi.client` в базовые пакеты для сканирования с помощью аннотации `@ComponentScan`:

```java

@SpringBootApplication
@ComponentScan(basePackages = {"com.greenapi.client", "com.example"})
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
    }
}
```

**Через конструктор:**

```java
var restTemplate=new RestTemplateBuilder().build();

    var greenApi1=new GreenApi(
    restTemplate,
    "https://media.green-api.com",
    "https://api.green-api.com",
    {{instanceId1}},
    {{instanceToken1}});

    var greenApi2=new GreenApi(
    restTemplate,
    "https://media.greenapi.com",
    "https://api.greenapi.com",
    {{instanceId2}},
    {{instanceToken2}});
```

### Как отправить сообщение

Ссылка на
пример: [sendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/sendMessageExample.java).

```java

@Log4j2
public class SendMessageExample {
    private void sendMessageExample(GreenApi greenApi) {
        var message = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("111111111111@c.us")
                .message("Привет!")
                .build());

        if (message.getStatusCode().is2xxSuccessful()) {
            log.info(message.getBody());
        } else {
            log.warn("Message isn't sent, status code: " + message.getStatusCode());
        }
    }
}
```

### Как создать группу и отправить сообщение

Ссылка на
пример: [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java).

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

### Как отправить файл загрузкой с диска

Чтобы отправить файл, нужно создать объект класса OutgoingFileByUpload и передать его в метод sendFileByUpload.
Поля OutgoingFileByUpload соответствуют полям json объекта запроса.

Ссылка на
пример: [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java).

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

### Как отправить файл по ссылке

Ссылка на
пример: [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java).

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

### Как отправить файл через uploadFile + sendFileByUrl

Ссылка на
пример: [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java).

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

### Как отправить опрос

Ссылка на
пример: [SendPollExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendPollExample.java).

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

### Как получать входящие уведомления

Чтобы начать получать уведомления, нужно передать функцию-обработчик в `webhookConsumer.start()`. Функция-обработчик
должна реализовывать интерфейс `WebhookHandler`. При получении нового уведомления ваша функция-обработчик будет
выполнена. Чтобы перестать получать уведомления, нужно вызвать функцию `webhookConsumer.stop()`.

`WebhookConsumer` - это класс ответственный за обработку сообщений, для его корректного функционирования нужны
`GreenApi` и `NotificationMapper`. Вы можете передать их в него через бины или через конструктор.

`NotificationMapper` - это bean ответственный за преобразование JSON объекта в java объект. Для этого он использует
`ОbjectMapper` из библиотеки `com.fasterxml.jackson` который должен быть доступен в качестве bean в конфигурации
или установлен через конструктор.

`WebhookHandler` - это интерфейс. Вы можете написать любой класс для обработки уведомлений,
просто реализуйте интерфейс и выполните свою логику в методе `handle()` или используйте лямбда выражение.

```java
public interface WebhookHandler {
    void handle(Notification notification);
}
```

Ссылка на
пример: [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java).

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

### Как работать с контактами

Ссылка на
пример: [ContactsMethodsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/ContactsMethodsExample.java).

```java

@Log4j2
class ContactsMethodsExample {

    private void addContact(GreenApi greenApi) {
        var addContactReq = AddContactReq.builder()
            .chatId("11001234567@c.us")
            .firstName("John")
            .lastName("Doe")
            .build();

        var addContactResp = greenApi.contacts.addContact(addContactReq).getBody();
    }

    private void editContact(GreenApi greenApi) {
        var editContactReq = EditContactReq.builder()
            .chatId("11001234567@c.us")
            .firstName("Jane")
            .lastName("Smith")
            .build();

        var editContactResp = greenApi.contacts.editContact(editContactReq).getBody();
    }

    private void deleteContact(GreenApi greenApi) {
        var deleteContactReq = DeleteContactReq.builder()
            .chatId("11001234567@c.us")
            .build();

        var deleteContactResp = greenApi.contacts.deleteContact(deleteContactReq).getBody();
    }
}
```

### Как переслать сообщения

Ссылка на пример: [ForwardMessagesExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/ForwardMessagesExample.java).

```java
@Log4j2
public class ForwardMessagesExample {

    private void forwardMessagesExample(GreenApi greenApi) {
        var response = greenApi.sending.forwardMessages(
            ForwardMessagesReq.builder()
                .chatId("11001234567@c.us")
                .chatIdFrom("11007654321@c.us")
                .messages(List.of("BAE5DBA5EC1D79E4", "BAE5DBA5EC1D79E5"))
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Forwarded message ids: " + Objects.requireNonNull(response.getBody()).getMessages());
        } else {
            log.warn("Forward failed, status: " + response.getStatusCode());
        }
    }
}
```

### Как отправить интерактивные кнопки

Ссылка на пример: [SendInteractiveButtonsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendInteractiveButtonsExample.java).

```java
@Log4j2
public class SendInteractiveButtonsExample {

    private void sendInteractiveButtonsExample(GreenApi greenApi) {
        var buttons = List.of(
            InteractiveButton.builder()
                .type("url")
                .buttonId("1")
                .buttonText("Открыть сайт")
                .url("https://green-api.com")
                .build(),
            InteractiveButton.builder()
                .type("call")
                .buttonId("2")
                .buttonText("Позвонить нам")
                .phoneNumber("11001234567")
                .build()
        );

        var response = greenApi.sending.sendInteractiveButtons(
            OutgoingInteractiveButtons.builder()
                .chatId("11001234567@c.us")
                .header("Поддержка")
                .body("Выберите действие")
                .footer("GREEN-API")
                .buttons(buttons)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Кнопки отправлены, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        }
    }

    private void sendInteractiveButtonsReplyExample(GreenApi greenApi) {
        var buttons = List.of(
            InteractiveReplyButton.builder().buttonId("1").buttonText("Да").build(),
            InteractiveReplyButton.builder().buttonId("2").buttonText("Нет").build()
        );

        var response = greenApi.sending.sendInteractiveButtonsReply(
            OutgoingInteractiveButtonsReply.builder()
                .chatId("11001234567@c.us")
                .body("Подтвердить заказ?")
                .footer("Заказ #12345")
                .buttons(buttons)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Кнопки-ответы отправлены, id: " + Objects.requireNonNull(response.getBody()).getIdMessage());
        }
    }
}
```

### Как показать индикатор набора текста

Ссылка на пример: [SendTypingExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendTypingExample.java).

```java
@Log4j2
public class SendTypingExample {

    private void sendTypingExample(GreenApi greenApi) {
        var response = greenApi.service.sendTyping(
            SendTypingReq.builder()
                .chatId("11001234567@c.us")
                .typingTime(3000)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Индикатор набора отправлен");
        }
    }

    private void sendRecordingExample(GreenApi greenApi) {
        var response = greenApi.service.sendTyping(
            SendTypingReq.builder()
                .chatId("11001234567@c.us")
                .typingTime(5000)
                .typingType("recording")
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Индикатор записи отправлен");
        }
    }
}
```

### Как получить список чатов

Ссылка на пример: [GetChatsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/GetChatsExample.java).

```java
@Log4j2
public class GetChatsExample {

    private void getAllChatsExample(GreenApi greenApi) {
        var response = greenApi.service.getChats();

        if (response.getStatusCode().is2xxSuccessful()) {
            var chats = Objects.requireNonNull(response.getBody());
            log.info("Всего чатов: " + chats.size());
            chats.forEach(chat -> log.info(chat.getId() + " | " + chat.getName()));
        }
    }

    private void getTopChatsExample(GreenApi greenApi) {
        var response = greenApi.service.getChats(10);

        if (response.getStatusCode().is2xxSuccessful()) {
            Objects.requireNonNull(response.getBody())
                .forEach(chat -> log.info(chat.getName() + " [архив: " + chat.getArchive() + "]"));
        }
    }
}
```

### Как получить журнал звонков

Ссылка на пример: [CallsJournalExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CallsJournalExample.java).

```java
@Log4j2
public class CallsJournalExample {

    private void lastIncomingCallsExample(GreenApi greenApi) {
        var response = greenApi.journals.lastIncomingCalls(60);

        if (response.getStatusCode().is2xxSuccessful()) {
            Objects.requireNonNull(response.getBody())
                .forEach(call -> log.info("Входящий от " + call.getChatId() + ", статус: " + call.getStatus()));
        }
    }

    private void lastOutgoingCallsExample(GreenApi greenApi) {
        var response = greenApi.journals.lastOutgoingCalls();

        if (response.getStatusCode().is2xxSuccessful()) {
            Objects.requireNonNull(response.getBody())
                .forEach(call -> log.info("Исходящий на " + call.getChatId() + ", длительность: " + call.getDuration() + "с"));
        }
    }
}
```

### Как обновить настройки группы

Ссылка на пример: [UpdateGroupSettingsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UpdateGroupSettingsExample.java).

```java
@Log4j2
public class UpdateGroupSettingsExample {

    private void lockGroupExample(GreenApi greenApi) {
        var response = greenApi.groups.updateGroupSettings(
            UpdateGroupSettingsReq.builder()
                .groupId("11001234567-1111111111@g.us")
                .allowParticipantsSendMessages(false)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Группа заблокирована: " + Objects.requireNonNull(response.getBody()).getUpdateGroupSettings());
        }
    }
}
```

### Как использовать методы аккаунта

Ссылка на пример: [AccountMethodsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/AccountMethodsExample.java).

```java
@Log4j2
public class AccountMethodsExample {

    private void getStateInstanceHistoryExample(GreenApi greenApi) {
        var response = greenApi.account.getStateInstanceHistory(50);

        if (response.getStatusCode().is2xxSuccessful()) {
            Objects.requireNonNull(response.getBody())
                .forEach(r -> log.info(r.getStateInstance() + " в " + r.getTimestamp()));
        }
    }

    private void updateApiTokenExample(GreenApi greenApi) {
        var response = greenApi.account.updateApiToken();

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Новый токен: " + Objects.requireNonNull(response.getBody()).getApiTokenInstance());
        }
    }
}
```

### Как отправить сообщение с имитацией набора и кастомным превью

Ссылка на пример: [SendMessageWithPreviewExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendMessageWithPreviewExample.java).

```java
@Log4j2
public class SendMessageWithPreviewExample {

    private void sendWithTypingExample(GreenApi greenApi) {
        var response = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("11001234567@c.us")
                .message("Привет! Я долго это писал...")
                .typingTime(4000)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Сообщение отправлено с имитацией набора");
        }
    }

    private void sendWithCustomPreviewExample(GreenApi greenApi) {
        var customPreview = CustomPreview.builder()
            .title("Документация GREEN-API")
            .description("Полный справочник WhatsApp API")
            .link("green-api.com")
            .build();

        var response = greenApi.sending.sendMessage(
            OutgoingMessage.builder()
                .chatId("11001234567@c.us")
                .message("Смотрите доку: https://green-api.com/docs/api/")
                .linkPreview(true)
                .typePreview("large")
                .customPreview(customPreview)
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Сообщение с кастомным превью отправлено");
        }
    }
}
```

Так как каждое уведомление автоматически кастится до java объекта, вы можете фильтровать уведомление по любому полю
самостоятельно.
С описанием структуры объектов уведомлений можно ознакомиться по этой
ссылке: [Документация](https://green-api.com/docs/api/receiving/notifications-format/type-webhook/)
Для удобства все типы хуков и сообщений названы аналогично документации:

| Java объект                          | Webhook's json объект                                                                                                                                     |
|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `TextMessageWebhook`                 | [TextMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/TextMessage/)                                                |
| `TemplateMessageWebhook`             | [TemplateMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/TemplateMessage/)                                        |
| `StickerMessageWebhook`              | [StickerMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/StickerMessage/)                                          |
| `ReactionMessageWebhook`             | [ReactionMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ReactionMessage/)                                        |
| `QuotedMessageWebhook`               | [QuotedMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/QuotedMessage/)                                            |
| `PollUpdateMessageWebhook`           | [PollUpdateMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/PollUpdateMessage/)                                    |
| `PollMessageWebhook`                 | [PollMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/PollMessage/)                                                |
| `LocationMessageWebhook`             | [LocationMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/LocationMessage/)                                        |
| `ListMessageWebhook`                 | [ListMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ListMessage/)                                                |
| `GroupInviteMessageWebhook`          | [GroupInviteMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/GroupInviteMessage/)                                  |
| `FileMessageWebhook`                 | [imageMessage, videoMessage, documentMessage, audioMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ImageMessage/) |
| `ExtendedTextMessageWebhook`         | [ExtendedTextMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ExtendedTextMessage/)                                |
| `ButtonsMessageWebhook`              | [ButtonsMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ButtonsMessage/)                                          |
| `ContactMessageWebhook`              | [ContactMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ContactMessage/)                                          |
| `ContactsArrayMessageWebhook`        | [ContactMessage](https://green-api.com/docs/api/receiving/notifications-format/incoming-message/ContactsArrayMessage/)                                    |
| `TemplateButtonsReplyMessageWebhook` | [TemplateButtonsReplyMessage](https://green-api.com/docs/api/receiving/notifications-format/selected-buttons/TemplateButtonsReplyMessage/)                |
| `ButtonsResponseMessageWebhook`      | [ButtonsResponseMessage](https://green-api.com/docs/api/receiving/notifications-format/selected-buttons/ButtonsResponseMessage/)                          |
| `ListResponseMessageWebhook`         | [ListResponseMessage](https://green-api.com/docs/api/receiving/notifications-format/selected-buttons/ListResponseMessage/)                                |

## Список примеров

| Описание                                        | Ссылка на пример                                                                                                                                                                      |
|-------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Как создать группу и отправить сообщение        | [SendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendMessageExample.java)                       |
| Как создать группу и отправить сообщение        | [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java) |
| Как отправить файл загруженный с устройства     | [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java)             |
| Как отправить файл через ссылку                 | [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java)                   |
| Как отправить файл через uploadFile + sendByUrl | [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java) |
| Как получать входящие уведомления               | [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java)                               |
| Как работать с контактами                       | [ContactsMethodsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/ContactsMethodsExample.java)                               |
| Как переслать сообщения                         | [ForwardMessagesExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/ForwardMessagesExample.java)                               |
| Как отправить интерактивные кнопки              | [SendInteractiveButtonsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendInteractiveButtonsExample.java)                 |
| Как показать индикатор набора текста/записи     | [SendTypingExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendTypingExample.java)                                         |
| Как получить список чатов                       | [GetChatsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/GetChatsExample.java)                                             |
| Как получить журнал входящих и исходящих звонков | [CallsJournalExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CallsJournalExample.java)                                    |
| Как обновить настройки группы                   | [UpdateGroupSettingsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UpdateGroupSettingsExample.java)                       |
| Как использовать getStateInstanceHistory и updateApiToken | [AccountMethodsExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/AccountMethodsExample.java)                       |
| Как удалить статус                              | [DeleteStatusExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/DeleteStatusExample.java)                                     |
| Как отправить сообщение с имитацией набора и кастомным превью | [SendMessageWithPreviewExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendMessageWithPreviewExample.java) |
| Как отправить файл с индикатором набора         | [SendFileWithTypingExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileWithTypingExample.java)                         |

## Список всех методов библиотеки

| Метод API                         | Описание                                                                                                                  | Documentation link                                                                                                        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `account.getSettings()`           | Метод предназначен для получения текущих настроек аккаунта                                                                | [GetSettings](https://green-api.com/docs/api/account/GetSettings)                                       |
| `account.setSettings()`           | Метод предназначен для установки настроек аккаунта                                                                        | [SetSettings](https://green-api.com/docs/api/account/SetSettings)                                       |
| `account.getStateInstance()`      | Метод предназначен для получения состояния аккаунта                                                                       | [GetStateInstance](https://green-api.com/docs/api/account/GetStateInstance)                             |
| `account.getStatusInstance()`     | Метод предназначен для получения состояния сокета соединения инстанса аккаунта с WhatsApp                                 | [GetStatusInstance](https://green-api.com/docs/api/account/GetStatusInstance)                           |
| `account.reboot()`                | Метод предназначен для перезапуска аккаунта                                                                               | [Reboot](https://green-api.com/docs/api/account/Reboot)                                                 |
| `account.logout()`                | Метод предназначен для разлогинивания аккаунта                                                                            | [Logout](https://green-api.com/docs/api/account/Logout)                                                 |
| `account.qr()`                    | Метод предназначен для получения QR-кода                                                                                  | [QR](https://green-api.com/docs/api/account/QR)                                                         |
| `account.getAuthorizationCode()`  | Метод предназначен для авторизации экземпляра по номеру телефона. Метод используется как альтернатива методу QR.          | [GetAuthorizationCode](https://green-api.com/docs/api/account/GetAuthorizationCode)                     |
| `account.setProfilePicture()`     | Метод предназначен для установки аватара аккаунта                                                                         | [SetProfilePicture](https://green-api.com/docs/api/account/SetProfilePicture)                           |
| `account.getStateInstanceHistory()` | Метод возвращает историю изменений состояния инстанса                                                                   | [GetStateInstanceHistory](https://green-api.com/docs/api/account/GetStateInstanceHistory/)              |
| `account.updateApiToken()`        | Метод генерирует новый API-токен для инстанса                                                                             | [UpdateApiToken](https://green-api.com/docs/api/account/UpdateApiToken/)                                |
| `contacts.addContact()`           | Метод предназначен для добавления контакта                                                                                | [AddContact](https://green-api.com/docs/api/contacts/AddContact)                         |
| `contacts.editContact()`          | Метод предназначен для изменения контакта                                                                                 | [EditContact](https://green-api.com/docs/api/contacts/EditContact)                        |
| `contacts.deleteContact()`        | Метод предназначен для удаления контакта                                                                                  | [DeleteContact](https://green-api.com/docs/api/contacts/DeleteContact)             |
| `device.getDeviceInfo()`          | Метод предназначен для получения информации об устройстве (телефоне), на котором запущено приложение WhatsApp Business    | [GetDeviceInfo](https://green-api.com/docs/api/phone/GetDeviceInfo)                                     |
| `groups.createGroup()`            | Метод предназначен для создания группового чата                                                                           | [CreateGroup](https://green-api.com/docs/api/groups/CreateGroup)                                        |
| `groups.updateGroupName()`        | Метод изменяет наименование группового чата                                                                               | [UpdateGroupName](https://green-api.com/docs/api/groups/UpdateGroupName)                                |
| `groups.getGroupData()`           | Метод получает данные группового чата                                                                                     | [GetGroupData](https://green-api.com/docs/api/groups/GetGroupData)                                      |
| `groups.addGroupParticipant()`    | Метод добавляет участника в групповой чат                                                                                 | [AddGroupParticipant](https://green-api.com/docs/api/groups/AddGroupParticipant)                        |
| `groups.removeGroupParticipant()` | Метод удаляет участника из группового чата                                                                                | [RemoveGroupParticipant](https://green-api.com/docs/api/groups/RemoveGroupParticipant)                  |
| `groups.setGroupAdmin()`          | Метод назначает участника группового чата администратором                                                                 | [SetGroupAdmin](https://green-api.com/docs/api/groups/SetGroupAdmin)                                    |
| `groups.removeAdmin()`            | Метод лишает участника прав администрирования группового чата                                                             | [RemoveAdmin](https://green-api.com/docs/api/groups/RemoveAdmin)                                        |
| `groups.setGroupPicture()`        | Метод устанавливает аватар группы                                                                                         | [SetGroupPicture](https://green-api.com/docs/api/groups/SetGroupPicture)                                |
| `groups.leaveGroup()`             | Метод производит выход пользователя текущего аккаунта из группового чата                                                  | [LeaveGroup](https://green-api.com/docs/api/groups/LeaveGroup)                                          |
| `groups.updateGroupSettings()`    | Метод обновляет настройки прав группы (кто может отправлять сообщения, редактировать информацию группы)                   | [UpdateGroupSettings](https://green-api.com/docs/api/groups/UpdateGroupSettings/)                       |
| `journals.getChatHistory()`       | Метод возвращает историю сообщений чата                                                                                   | [GetChatHistory](https://green-api.com/docs/api/journals/GetChatHistory)                                |
| `journals.getMessage()`           | Метод возвращает сообщение чата                                                                                           | [GetMessage](https://green-api.com/docs/api/journals/GetMessage)                                        |
| `journals.lastIncomingMessages()` | Метод возвращает крайние входящие сообщения аккаунта                                                                      | [LastIncomingMessages](https://green-api.com/docs/api/journals/LastIncomingMessages)                    |
| `journals.lastOutgoingMessages()` | Метод возвращает крайние отправленные сообщения аккаунта                                                                  | [LastOutgoingMessages](https://green-api.com/docs/api/journals/LastOutgoingMessages)                    |
| `journals.lastIncomingCalls()`    | Метод возвращает последние входящие звонки аккаунта                                                                       | [LastIncomingCalls](https://green-api.com/docs/api/journals/LastIncomingCalls/)                         |
| `journals.lastOutgoingCalls()`    | Метод возвращает последние исходящие звонки аккаунта                                                                      | [LastOutgoingCalls](https://green-api.com/docs/api/journals/LastOutgoingCalls/)                         |
| `queues.showMessagesQueue()`      | Метод предназначен для получения списка сообщений, находящихся в очереди на отправку                                      | [ShowMessagesQueue](https://green-api.com/docs/api/queues/ShowMessagesQueue)                            |
| `queues.clearMessagesQueue()`     | Метод предназначен для очистки очереди сообщений на отправку                                                              | [ClearMessagesQueue](https://green-api.com/docs/api/queues/ClearMessagesQueue)                          |
| `readMark.readChat()`             | Метод предназначен для отметки сообщений в чате прочитанными                                                              | [ReadChat](https://green-api.com/docs/api/marks/ReadChat)                                               |
| `receiving.receiveNotification()` | Метод предназначен для получения одного входящего уведомления из очереди уведомлений                                      | [ReceiveNotification](https://green-api.com/docs/api/receiving/technology-http-api/ReceiveNotification) |
| `receiving.deleteNotification()`  | Метод предназначен для удаления входящего уведомления из очереди уведомлений                                              | [DeleteNotification](https://green-api.com/docs/api/receiving/technology-http-api/DeleteNotification)   |
| `receiving.downloadFile()`        | Метод предназначен для скачивания принятых и отправленных файлов                                                          | [DownloadFile](https://green-api.com/docs/api/receiving/files/DownloadFile)                             |
| `sending.sendMessage()`           | Метод предназначен для отправки текстового сообщения в личный или групповой чат                                           | [SendMessage](https://green-api.com/docs/api/sending/SendMessage)                                       |
| `sending.sendButtons()`           | Метод предназначен для отправки сообщения с кнопками в личный или групповой чат                                           | [SendButtons](https://green-api.com/docs/api/sending/SendButtons)                                       |
| `sending.sendTemplateButtons()`   | Метод предназначен для отправки сообщения с интерактивными кнопками из перечня шаблонов в личный или групповой чат        | [SendTemplateButtons](https://green-api.com/docs/api/sending/SendTemplateButtons)                       |
| `sending.sendPoll()`              | Метод предназначен для отправки опроса в личный или групповой чат                                                         | [SendPoll](https://green-api.com/docs/api/sending/SendPoll)                                             |
| `sending.sendListMessage()`       | Метод предназначен для отправки сообщения с кнопкой выбора из списка значений в личный или групповой чат                  | [SendListMessage](https://green-api.com/docs/api/sending/SendListMessage)                               |
| `sending.sendFileByUpload()`      | Метод предназначен для отправки файла, загружаемого через форму (form-data)                                               | [SendFileByUpload](https://green-api.com/docs/api/sending/SendFileByUpload)                             |
| `sending.sendFileByUrl()`         | Метод предназначен для отправки файла, загружаемого по ссылке                                                             | [SendFileByUrl](https://green-api.com/docs/api/sending/SendFileByUrl)                                   |
| `sending.uploadFile()`            | Метод предназначен для загрузки файла в облачное хранилище, который можно отправить методом SendFileByUrl                 | [UploadFile](https://green-api.com/docs/api/sending/UploadFile)                                         |
| `sending.sendLocation()`          | Метод предназначен для отправки сообщения геолокации                                                                      | [SendLocation](https://green-api.com/docs/api/sending/SendLocation)                                     |
| `sending.sendContact()`           | Метод предназначен для отправки сообщения с контактом                                                                     | [SendContact](https://green-api.com/docs/api/sending/SendContact)                                       |
| `sending.sendLink()`              | Метод предназначен для отправки сообщения со ссылкой, по которой будут добавлены превью изображения, заголовок и описание | [SendLink](https://green-api.com/docs/api/sending/SendLink)                                             |
| `sending.forwardMessages()`       | Метод предназначен для пересылки сообщений в личный или групповой чат                                                     | [ForwardMessages](https://green-api.com/docs/api/sending/ForwardMessages)                               |
| `sending.sendInteractiveButtons()` | Метод отправляет сообщение с кнопками-действиями (url, звонок, копирование кода) в чат                                  | [SendInteractiveButtons](https://green-api.com/docs/api/sending/SendInteractiveButtons/)                |
| `sending.sendInteractiveButtonsReply()` | Метод отправляет сообщение с кнопками-ответами в чат. Бета-функция                                                 | [SendInteractiveButtonsReply](https://green-api.com/docs/api/sending/SendInteractiveButtonsReply/)      |
| `service.checkWhatsapp()`         | Метод проверяет наличие аккаунта WhatsApp на номере телефона                                                              | [CheckWhatsapp](https://green-api.com/docs/api/service/CheckWhatsapp)                                   |
| `service.getAvatar()`             | Метод возвращает аватар корреспондента или группового чата                                                                | [GetAvatar](https://green-api.com/docs/api/service/GetAvatar)                                           |
| `service.getContacts()`           | Метод предназначен для получения списка контактов текущего аккаунта                                                       | [GetContacts](https://green-api.com/docs/api/service/GetContacts)                                       |
| `service.getContactInfo()`        | Метод предназначен для получения информации о контакте                                                                    | [GetContactInfo](https://green-api.com/docs/api/service/GetContactInfo)                                 |
| `service.deleteMessage()`         | Метод удаляет сообщение из чата                                                                                           | [DeleteMessage](https://green-api.com/docs/api/service/deleteMessage)                                   |
| `service.archiveChat()`           | Метод архивирует чат                                                                                                      | [ArchiveChat](https://green-api.com/docs/api/service/archiveChat)                                       |
| `service.unarchiveChat()`         | Метод разархивирует чат                                                                                                   | [UnarchiveChat](https://green-api.com/docs/api/service/unarchiveChat)                                   |
| `service.setDisappearingChat()`   | Метод предназначен для изменения настроек исчезающих сообщений в чатах                                                    | [SetDisappearingChat](https://green-api.com/docs/api/service/SetDisappearingChat)                       |
| `service.editMessage()`           | Метод предназначен для редактирования текстового сообщения в чате                                                         | [EditMessage](https://green-api.com/docs/api/service/EditMessage/)                                      |
| `service.sendTyping()`            | Метод показывает получателю индикатор набора текста или записи аудио                                                      | [SendTyping](https://green-api.com/docs/api/service/SendTyping/)                                        |
| `service.getChats()`              | Метод возвращает список чатов текущего аккаунта                                                                           | [GetChats](https://green-api.com/docs/api/service/GetChats/)                                            |
| `webhook.start()`                 | Метод предназначен для старта получения новых уведомлений                                                                 |                                                                                                                           |
| `webhook.stop()`                  | Метод предназначен для остановки получения новых уведомлений                                                              |                                                                                                                           |
| `statuses.sendTextStatus()`              | Метод предназначен для отправки текстового статуса                                                                        | [SendTextStatus](https://green-api.com/docs/api/statuses/SendTextStatus/)                                |
| `statuses.sendVoiceStatus()`             | Метод предназначен для отправки голосового статуса                                                                        | [SendVoiceStatus](https://green-api.com/docs/api/statuses/SendVoiceStatus/)                              |
| `statuses.sendMediaStatus()`             | Метод предназначен для отправки медиа-файлов                                                                              | [SendMediaStatus](https://green-api.com/docs/api/statuses/SendMediaStatus/)                              |
| `statuses.getIncomingStatuses()`         | Метод возвращает крайние входящие статусы аккаунта                                                                        | [GetIncomingStatuses](https://green-api.com/docs/api/statuses/GetIncomingStatuses/)                      |
| `statuses.getOutgoingStatuses()`         | Метод возвращает крайние отправленные статусы аккаунта                                                                    | [GetOutgoingStatuses](https://green-api.com/docs/api/statuses/GetOutgoingStatuses/)                      |
| `statuses.getStatusStatistic()`          | Метод возвращает массив получателей со статусами, отмеченных как отправлено/доставлено/прочитано, для данного статуса     | [GetStatusStatistic](https://green-api.com/docs/api/statuses/GetStatusStatistic/)                        |
| `statuses.deleteStatus()`                | Метод удаляет отправленный статус                                                                                         | [DeleteStatus](https://green-api.com/docs/api/statuses/DeleteStatus/)                                    |

## Документация по методам сервиса

[Документация по методам сервиса](https://green-api.com/docs/api/)

## Лицензия

Лицензировано на условиях [
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
](https://creativecommons.org/licenses/by-nd/4.0/).
Смотрите
файл [LICENSE](https://github.com/green-api/whatsapp-api-client-java/blob/master/LICENSE.txt).