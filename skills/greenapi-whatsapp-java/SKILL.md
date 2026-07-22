---
name: greenapi-whatsapp-java
version: 1.0.0
description: Write correct Java code with the GREEN-API WhatsApp SDK (com.green-api:whatsapp-api-client-java). Use when sending/receiving WhatsApp messages, files, polls, statuses, or managing groups/contacts/instance settings via GREEN-API in a Java or Spring Boot project. Covers client initialization, method groups, chatId formats, notification handling, and common pitfalls.
---

# GREEN-API WhatsApp Java SDK

Library `com.green-api:whatsapp-api-client-java` — a Java wrapper over the GREEN-API REST API
(https://green-api.com/en/docs/api/). Requires an **instanceId** and **apiTokenInstance** from
https://console.green-api.com/, and the instance's WhatsApp account must be **authorized**
(QR code in the console, or `account.getAuthorizationCode(phoneNumber)`).

## When to use this skill

Any task that sends or receives WhatsApp messages through GREEN-API from Java: bots,
notification services, group management, polling or webhook-based message intake.

## Installation

Maven (the SDK is built on Spring Boot 3.x — Java 17+; it pulls `spring-boot-starter-web`):

```xml
<dependency>
  <groupId>com.green-api</groupId>
  <artifactId>whatsapp-api-client-java</artifactId>
  <version>[0.2.2,)</version>
</dependency>
```

## Client initialization

Entry point: `com.greenapi.client.pkg.api.GreenApi`. It exposes public fields, one per method
group: `account`, `contacts`, `sending`, `journals`, `groups`, `queues`, `marking`,
`receiving`, `service`, `statuses`.

**Constructor argument order is a common trap: media host comes BEFORE api host.**

```java
import com.greenapi.client.pkg.api.GreenApi;
import org.springframework.web.client.RestTemplate;

var greenApi = new GreenApi(
    new RestTemplate(),
    "https://media.green-api.com",   // hostMedia — FIRST
    "https://api.green-api.com",     // host — SECOND
    System.getenv("GREEN_API_ID_INSTANCE"),
    System.getenv("GREEN_API_TOKEN"));
```

In Spring Boot you can instead define properties `green-api.host`, `green-api.hostMedia`,
`green-api.instanceId`, `green-api.token`, provide a `RestTemplate` bean, and add
`com.greenapi.client` to `@ComponentScan` — `GreenApi`, `WebhookConsumer` and
`NotificationMapper` are `@Component`s.

Every SDK method returns Spring's `ResponseEntity<T>`; call `.getBody()` for the payload and
`.getStatusCode()` to check success. Request objects are built with Lombok builders.

## Core scenarios

### Check the instance is authorized (do this first)

```java
var state = greenApi.account.getStateInstance().getBody();
// state.getStateInstance() must be "authorized"; if "notAuthorized" — scan QR in console
```

### Send a text message

```java
import com.greenapi.client.pkg.models.request.OutgoingMessage;

var resp = greenApi.sending.sendMessage(OutgoingMessage.builder()
    .chatId("79876543210@c.us")      // personal chat; groups: "...@g.us"
    .message("Hello!")               // max 20000 chars, UTF-8
    .build());
if (resp.getStatusCode().is2xxSuccessful()) {
    System.out.println("id: " + resp.getBody().getIdMessage());
}
```

### Send a file

```java
import com.greenapi.client.pkg.models.request.OutgoingFileByUrl;
import com.greenapi.client.pkg.models.request.OutgoingFileByUpload;

// by URL (max 100 MB; fileName MUST include the extension)
greenApi.sending.sendFileByUrl(OutgoingFileByUrl.builder()
    .chatId("79876543210@c.us")
    .urlFile("https://example.com/report.pdf")
    .fileName("report.pdf")
    .caption("caption, max 1024 chars")
    .build());

// by upload from disk
greenApi.sending.sendFileByUpload(OutgoingFileByUpload.builder()
    .chatId("79876543210@c.us")
    .file(new java.io.File("/path/report.pdf"))
    .fileName("report.pdf")
    .build());
```

### Receive notifications — polling loop

Preferred: the built-in consumer (blocking loop; it deletes each notification after your
handler runs):

```java
import com.greenapi.client.pkg.api.webhook.WebhookConsumer;
import com.greenapi.client.pkg.api.webhook.NotificationMapper;
import com.greenapi.client.pkg.models.notifications.TextMessageWebhook;

var consumer = new WebhookConsumer(greenApi, new NotificationMapper());
consumer.start(notification -> {                    // WebhookHandler lambda
    if (notification.getBody() instanceof TextMessageWebhook text) {
        System.out.println(text.getMessageData().getTextMessageData().getTextMessage());
    }
});
// consumer.stop() from another thread to exit the loop
```

Manual polling uses `greenApi.receiving.receiveNotification()` (long-poll, ~5 s wait; body is
a raw JSON `String`, literally `"null"` when the queue is empty) and **must** call
`greenApi.receiving.deleteNotification(receiptId)` after processing — otherwise the same
notification is redelivered. The queue keeps notifications for 24 h.

**Polling does not work while a webhook URL is set on the instance** — clear `webhookUrl` in
instance settings first. To receive via your own HTTP webhook endpoint instead, set
`webhookUrl` in the console and parse incoming POST JSON with `NotificationMapper.get(json)`.

## Pitfalls checklist

- **chatId format**: personal `"<countrycode+number>@c.us"` (digits only, no `+`), group
  `"<id>@g.us"`. But `service.checkWhatsapp(Long)` and `account.getAuthorizationCode(Long)`
  take a bare `Long` phone number, not a chatId.
- **Constructor order**: `GreenApi(restTemplate, hostMedia, host, instanceId, token)`.
- **Authorization**: unauthorized instance queues messages (up to 24 h) instead of sending;
  verify `getStateInstance()` == `"authorized"`.
- **Send rate**: GREEN-API queues outgoing messages server-side; pace bulk sends with the
  `delaySendMessagesMilliseconds` instance setting (min 500 ms) rather than client-side
  hammering. Mass messaging to unknown numbers risks a WhatsApp ban.
- **`createGroup` with a non-existent number can get the sender's number banned by WhatsApp.**
- Status request classes are spelled `...Resq` in this SDK (`SendTextStatusResq`,
  `SendVoiceStatusResq`, `SendMediaStatusResq`) — not `Req`. Account settings setter is
  `account.setSetting(...)` (singular). Settings apply within ~5 min and reboot the instance.
- `sendFileByUrl`: URL must be a direct file link, `http(s)://`, no spaces or `~$€%#£?!`.

## Method reference (only methods that exist in the SDK)

- [references/sending.md](references/sending.md) — `sending.*`: messages, files, buttons,
  lists, polls, location, contact, forwarding.
- [references/receiving.md](references/receiving.md) — `receiving.*`, `WebhookConsumer`,
  all `*Webhook` notification classes and `typeWebhook` mapping.
- [references/account-service.md](references/account-service.md) — `account.*`, `service.*`,
  `marking.*`, `queues.*`.
- [references/groups-contacts-statuses.md](references/groups-contacts-statuses.md) —
  `groups.*`, `contacts.*`, `statuses.*`, `journals.*`.

Do not invent methods not listed in these files — the SDK implements only these.
