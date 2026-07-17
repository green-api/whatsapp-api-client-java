# Receiving notifications — `greenApi.receiving.*` and the webhook layer

## HTTP polling methods (class `GreenApiReceiving`)

### receiveNotification() → ResponseEntity<String>
Docs: https://green-api.com/en/docs/api/receiving/technology-http-api/ReceiveNotification/
Long-polls the notification queue (server waits ~5 s; returns literal string `"null"` if
empty). The JSON contains `receiptId` and `body`. Queue is FIFO, notifications are kept 24 h.
**Does not work while a webhook URL is configured on the instance** — clear it and wait ~1 min.

### deleteNotification(Integer receiptId) → ResponseEntity<String>
Docs: https://green-api.com/en/docs/api/receiving/technology-http-api/DeleteNotification/
Must be called after processing each notification, otherwise it is redelivered.

### downloadFile(MessageReq) → ResponseEntity<byte[]>
Docs: https://green-api.com/en/docs/api/receiving/files/DownloadFile/
`MessageReq` builder: `chatId`, `idMessage`. Returns the file bytes of an incoming/outgoing
media message. (For incoming media you can also use the `downloadUrl` field from the
`FileMessageWebhook` payload.)

## WebhookConsumer — ready-made polling loop

Package `com.greenapi.client.pkg.api.webhook`. Classes: `WebhookConsumer`, `WebhookHandler`
(functional interface `void handle(Notification notification)`), `NotificationMapper`.

```java
var consumer = new WebhookConsumer(greenApi, new NotificationMapper());
consumer.start(notification -> { /* runs for each notification */ });
// blocking loop; call consumer.stop() from another thread to end it
```

`start()` receives, maps JSON → typed `Notification`, calls your handler, then deletes the
notification. On mapping failure it deletes and continues; on unexpected errors it sleeps 5 s
and retries.

For a push-style webhook endpoint (your own HTTP server registered as `webhookUrl` in
instance settings), reuse `NotificationMapper.get(jsonString)` to parse the POSTed JSON into
the same `Notification` type. The mapper handles both queue format (`{receiptId, body}`) and
raw webhook format (`{typeWebhook, ...}`).

## Notification model

`com.greenapi.client.pkg.models.notifications.Notification`:
- `getReceiptId()` — Integer (null for direct webhook JSON)
- `getBody()` — `NotificationBody` (abstract; check concrete subclass with `instanceof`).
  `getBody().getTypeWebhook()` returns the raw type string.

### typeWebhook → Java class (all in `com.greenapi.client.pkg.models.notifications`)

| typeWebhook | Class |
|---|---|
| `outgoingMessageStatus` | `OutgoingMessageStatus` |
| `stateInstanceChanged` / `statusInstanceChanged` | `StatusInstanceChanged` / `StateInstanceChanged` |
| `deviceInfo` | `DeviceInfo` |
| `incomingCall` | `IncomingCall` |
| `incomingBlock` | `IncomingBlock` |

For `incomingMessageReceived` / `outgoingMessageReceived` / `outgoingAPIMessageReceived` the
class is chosen by `messageData.typeMessage`:

| typeMessage | Class |
|---|---|
| `textMessage` | `TextMessageWebhook` |
| `extendedTextMessage` | `ExtendedTextMessageWebhook` |
| `quotedMessage` | `QuotedMessageWebhook` |
| `imageMessage`, `videoMessage`, `documentMessage`, `audioMessage` | `FileMessageWebhook` |
| `locationMessage` | `LocationMessageWebhook` |
| `contactMessage` / `contactsArrayMessage` | `ContactMessageWebhook` / `ContactsArrayMessageWebhook` |
| `stickerMessage` | `StickerMessageWebhook` |
| `reactionMessage` | `ReactionMessageWebhook` |
| `pollMessage` / `pollUpdateMessage` | `PollMessageWebhook` / `PollUpdateMessageWebhook` |
| `listMessage` / `listResponseMessage` | `ListMessageWebhook` / `ListResponseMessageWebhook` |
| `buttonsMessage` / `buttonsResponseMessage` | `ButtonsMessageWebhook` / `ButtonsResponseMessageWebhook` |
| `templateMessage` / `templateButtonsReplyMessage` | `TemplateMessageWebhook` / `TemplateButtonsReplyMessageWebhook` |
| `groupInviteMessage` | `GroupInviteMessageWebhook` |

Notification payload format docs:
https://green-api.com/en/docs/api/receiving/notifications-format/type-webhook/

Typical echo-bot handler:

```java
consumer.start(n -> {
    if (n.getBody() instanceof TextMessageWebhook t
            && "incomingMessageReceived".equals(t.getTypeWebhook())) {
        var chatId = t.getSenderData().getChatId();
        var text = t.getMessageData().getTextMessageData().getTextMessage();
        greenApi.sending.sendMessage(
            OutgoingMessage.builder().chatId(chatId).message(text).build());
    }
});
```
