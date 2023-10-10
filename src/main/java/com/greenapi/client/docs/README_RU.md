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

Или используйте готовый бин, который клиент создается на основе параметров application.yml. Для этого укажите параметры вашего
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

### Как отправить сообщение

Ссылка на пример: [sendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/sendMessageExample.java).

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

Ссылка на пример: [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java).

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

Ссылка на пример: [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java).

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

Ссылка на пример: [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java).

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

Ссылка на пример: [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java).

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

Ссылка на пример: [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java).

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

## Список примеров

| Описание                                        | Ссылка на пример                                                                                                                                                                      |
|-------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Как создать группу и отправить сообщение        | [SendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendMessageExample.java)                       |
| Как создать группу и отправить сообщение        | [CreateGroupSendMessageExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/CreateGroupSendMessageExample.java) |
| Как отправить файл загруженный с устройства     | [SendFileByUploadExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUploadExample.java)             |
| Как отправить файл через ссылку                 | [SendFileByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/SendFileByUrlExample.java)                   |
| Как отправить файл через uploadFile + sendByUrl | [UploadFileAndSendByUrlExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/UploadFileAndSendByUrlExample.java) |
| Как получать входящие уведомления               | [WebhookExample.java](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/client/examples/WebhookExample.java)                               |

## Список всех методов библиотеки

| Метод API                          | Описание                                                                                                                  | Documentation link                                                                                       |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| `account.getSettings()`            | Метод предназначен для получения текущих настроек аккаунта                                                                | [GetSettings](https://green-api.com/docs/api/account/GetSettings/)                                       |
| `account.setSettings()`            | Метод предназначен для установки настроек аккаунта                                                                        | [SetSettings](https://green-api.com/docs/api/account/SetSettings/)                                       |
| `account.getStateInstance()`       | Метод предназначен для получения состояния аккаунта                                                                       | [GetStateInstance](https://green-api.com/docs/api/account/GetStateInstance/)                             |
| `account.getStatusInstance()`      | Метод предназначен для получения состояния сокета соединения инстанса аккаунта с WhatsApp                                 | [GetStatusInstance](https://green-api.com/docs/api/account/GetStatusInstance/)                           |
| `account.reboot()`                 | Метод предназначен для перезапуска аккаунта                                                                               | [Reboot](https://green-api.com/docs/api/account/Reboot/)                                                 |
| `account.logout()`                 | Метод предназначен для разлогинивания аккаунта                                                                            | [Logout](https://green-api.com/docs/api/account/Logout/)                                                 |
| `account.qr()`                     | Метод предназначен для получения QR-кода                                                                                  | [QR](https://green-api.com/docs/api/account/QR/)                                                         |
| `account.SetProfilePicture()`      | Метод предназначен для установки аватара аккаунта                                                                         | [SetProfilePicture](https://green-api.com/docs/api/account/SetProfilePicture/)                           |
| `device.GetDeviceInfo()`           | Метод предназначен для получения информации об устройстве (телефоне), на котором запущено приложение WhatsApp Business    | [GetDeviceInfo](https://green-api.com/docs/api/phone/GetDeviceInfo/)                                     |
| `groups.CreateGroup()`             | Метод предназначен для создания группового чата                                                                           | [CreateGroup](https://green-api.com/docs/api/groups/CreateGroup/)                                        |
| `groups.UpdateGroupName()`         | Метод изменяет наименование группового чата                                                                               | [UpdateGroupName](https://green-api.com/docs/api/groups/UpdateGroupName/)                                |
| `groups.GetGroupData()`            | Метод получает данные группового чата                                                                                     | [GetGroupData](https://green-api.com/docs/api/groups/GetGroupData/)                                      |
| `groups.AddGroupParticipant()`     | Метод добавляет участника в групповой чат                                                                                 | [AddGroupParticipant](https://green-api.com/docs/api/groups/AddGroupParticipant/)                        |
| `groups.RemoveGroupParticipant()`  | Метод удаляет участника из группового чата                                                                                | [RemoveGroupParticipant](https://green-api.com/docs/api/groups/RemoveGroupParticipant/)                  |
| `groups.SetGroupAdmin()`           | Метод назначает участника группового чата администратором                                                                 | [SetGroupAdmin](https://green-api.com/docs/api/groups/SetGroupAdmin/)                                    |
| `groups.RemoveAdmin()`             | Метод лишает участника прав администрирования группового чата                                                             | [RemoveAdmin](https://green-api.com/docs/api/groups/RemoveAdmin/)                                        |
| `groups.SetGroupPicture()`         | Метод устанавливает аватар группы                                                                                         | [SetGroupPicture](https://green-api.com/docs/api/groups/SetGroupPicture/)                                |
| `groups.LeaveGroup()`              | Метод производит выход пользователя текущего аккаунта из группового чата                                                  | [LeaveGroup](https://green-api.com/docs/api/groups/LeaveGroup/)                                          |
| `journals.GetChatHistory()`        | Метод возвращает историю сообщений чата                                                                                   | [GetChatHistory](https://green-api.com/docs/api/journals/GetChatHistory/)                                |
| `journals.GetMessage()`            | Метод возвращает сообщение чата                                                                                           | [GetMessage](https://green-api.com/docs/api/journals/GetMessage/)                                        |
| `journals.LastIncomingMessages()`  | Метод возвращает крайние входящие сообщения аккаунта                                                                      | [LastIncomingMessages](https://green-api.com/docs/api/journals/LastIncomingMessages/)                    |
| `journals.LastOutgoingMessages()`  | Метод возвращает крайние отправленные сообщения аккаунта                                                                  | [LastOutgoingMessages](https://green-api.com/docs/api/journals/LastOutgoingMessages/)                    |
| `queues.ShowMessagesQueue()`       | Метод предназначен для получения списка сообщений, находящихся в очереди на отправку                                      | [ShowMessagesQueue](https://green-api.com/docs/api/queues/ShowMessagesQueue/)                            |
| `queues.ClearMessagesQueue()`      | Метод предназначен для очистки очереди сообщений на отправку                                                              | [ClearMessagesQueue](https://green-api.com/docs/api/queues/ClearMessagesQueue/)                          |
| `readMark.ReadChat()`              | Метод предназначен для отметки сообщений в чате прочитанными                                                              | [ReadChat](https://green-api.com/docs/api/marks/ReadChat/)                                               |
| `receiving.ReceiveNotification()`  | Метод предназначен для получения одного входящего уведомления из очереди уведомлений                                      | [ReceiveNotification](https://green-api.com/docs/api/receiving/technology-http-api/ReceiveNotification/) |
| `receiving.DeleteNotification()`   | Метод предназначен для удаления входящего уведомления из очереди уведомлений                                              | [DeleteNotification](https://green-api.com/docs/api/receiving/technology-http-api/DeleteNotification/)   |
| `receiving.DownloadFile()`         | Метод предназначен для скачивания принятых и отправленных файлов                                                          | [DownloadFile](https://green-api.com/docs/api/receiving/files/DownloadFile/)                             |
| `sending.SendMessage()`            | Метод предназначен для отправки текстового сообщения в личный или групповой чат                                           | [SendMessage](https://green-api.com/docs/api/sending/SendMessage/)                                       |
| `sending.SendButtons()`            | Метод предназначен для отправки сообщения с кнопками в личный или групповой чат                                           | [SendButtons](https://green-api.com/docs/api/sending/SendButtons/)                                       |
| `sending.SendTemplateButtons()`    | Метод предназначен для отправки сообщения с интерактивными кнопками из перечня шаблонов в личный или групповой чат        | [SendTemplateButtons](https://green-api.com/docs/api/sending/SendTemplateButtons/)                       |
| `sending.SendListMessage()`        | Метод предназначен для отправки сообщения с кнопкой выбора из списка значений в личный или групповой чат                  | [SendListMessage](https://green-api.com/docs/api/sending/SendListMessage/)                               |
| `sending.SendFileByUpload()`       | Метод предназначен для отправки файла, загружаемого через форму (form-data)                                               | [SendFileByUpload](https://green-api.com/docs/api/sending/SendFileByUpload/)                             |
| `sending.SendFileByUrl()`          | Метод предназначен для отправки файла, загружаемого по ссылке                                                             | [SendFileByUrl](https://green-api.com/docs/api/sending/SendFileByUrl/)                                   |
| `sending.UploadFile()`             | Метод предназначен для загрузки файла в облачное хранилище, который можно отправить методом SendFileByUrl                 | [UploadFile](https://green-api.com/docs/api/sending/UploadFile/)                                         |
| `sending.SendLocation()`           | Метод предназначен для отправки сообщения геолокации                                                                      | [SendLocation](https://green-api.com/docs/api/sending/SendLocation/)                                     |
| `sending.SendContact()`            | Метод предназначен для отправки сообщения с контактом                                                                     | [SendContact](https://green-api.com/docs/api/sending/SendContact/)                                       |
| `sending.SendLink()`               | Метод предназначен для отправки сообщения со ссылкой, по которой будут добавлены превью изображения, заголовок и описание | [SendLink](https://green-api.com/docs/api/sending/SendLink/)                                             |
| `sending.ForwardMessages()`        | Метод предназначен для пересылки сообщений в личный или групповой чат                                                     | [ForwardMessages](https://green-api.com/docs/api/sending/ForwardMessages/)                               |
| `service.CheckWhatsapp()`          | Метод проверяет наличие аккаунта WhatsApp на номере телефона                                                              | [CheckWhatsapp](https://green-api.com/docs/api/service/CheckWhatsapp/)                                   |
| `service.GetAvatar()`              | Метод возвращает аватар корреспондента или группового чата                                                                | [GetAvatar](https://green-api.com/docs/api/service/GetAvatar/)                                           |
| `service.GetContacts()`            | Метод предназначен для получения списка контактов текущего аккаунта                                                       | [GetContacts](https://green-api.com/docs/api/service/GetContacts/)                                       |
| `service.GetContactInfo()`         | Метод предназначен для получения информации о контакте                                                                    | [GetContactInfo](https://green-api.com/docs/api/service/GetContactInfo/)                                 |
| `service.DeleteMessage()`          | Метод удаляет сообщение из чата                                                                                           | [DeleteMessage](https://green-api.com/docs/api/service/deleteMessage/)                                   |
| `service.ArchiveChat()`            | Метод архивирует чат                                                                                                      | [ArchiveChat](https://green-api.com/docs/api/service/archiveChat/)                                       |
| `service.UnarchiveChat()`          | Метод разархивирует чат                                                                                                   | [UnarchiveChat](https://green-api.com/docs/api/service/unarchiveChat/)                                   |
| `service.SetDisappearingChat()`    | Метод предназначен для изменения настроек исчезающих сообщений в чатах                                                    | [SetDisappearingChat](https://green-api.com/docs/api/service/SetDisappearingChat/)                       |
| `webhook.Start()`                  | Метод предназначен для старта получения новых уведомлений                                                                 |                                                                                                          |
| `webhook.Stop()`                   | Метод предназначен для остановки получения новых уведомлений                                                              |                                                                                                          |

## Документация по методам сервиса

[Документация по методам сервиса](https://green-api.com/docs/api/)

## Лицензия

Лицензировано на условиях [
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)
](https://creativecommons.org/licenses/by-nd/4.0/).
Смотрите
файл [LICENSE](https://github.com/green-api/whatsapp-api-client-java/blob/master/LICENSE.txt).