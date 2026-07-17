# Account, service, marking and queue methods

All return `ResponseEntity<T>`; DTOs in `com.greenapi.client.pkg.models.request`, responses
in `...models.response`.

## `greenApi.account.*` (class `GreenApiAccount`)

| Method | Docs | Notes |
|---|---|---|
| `getSettings()` → `Settings` | api/account/GetSettings/ | current instance settings |
| `getWaSettings()` → `WaSettings` | api/account/GetWaSettings/ | WhatsApp account info (phone, avatar, state) |
| `setSetting(InstanceSettingsReq)` → `SetSettingsResp` | api/account/SetSettings/ | **name is singular `setSetting`**; only non-null fields are sent; applies within ~5 min and reboots the instance |
| `getStateInstance()` → `StateInstanceResp` | api/account/GetStateInstance/ | `getStateInstance()` string: `authorized`, `notAuthorized`, `blocked`, `starting`, `yellowCard` |
| `getStatusInstance()` → `StatusInstanceResp` | api/account/GetStatusInstance/ | socket connection status: `online`/`offline` |
| `reboot()` → `RebootResp` | api/account/Reboot/ | |
| `logout()` → `LogoutResp` | api/account/Logout/ | un-authorizes the instance |
| `getQrCode()` → `Qr` | api/account/QR/ | QR for authorization (poll ~once/sec; QR lifetime 20 s) |
| `getAuthorizationCode(Long phoneNumber)` → `GetAuthorizationCodeResp` | api/account/GetAuthorizationCode/ | phone as Long, e.g. `79876543210L` |
| `setProfilePicture(java.io.File)` → `SetProfilePictureResp` | api/account/SetProfilePicture/ | JPEG |
| `getStateInstanceHistory()` / `getStateInstanceHistory(Integer count)` → `List<StateInstanceHistoryRecord>` | docs/api/account/GetStateInstanceHistory/ | |
| `updateApiToken()` → `UpdateApiTokenResp` | docs/api/account/UpdateApiToken/ | old token stops working — store the new one |

`InstanceSettingsReq` notable builder fields (all optional; webhook toggles take `"yes"`/`"no"`
strings): `webhookUrl`, `webhookUrlToken`, `delaySendMessagesMilliseconds` (Integer, min 500),
`markIncomingMessagesReaded`, `markIncomingMessagesReadedOnReply`, `outgoingWebhook`,
`outgoingMessageWebhook`, `outgoingAPIMessageWebhook`, `incomingWebhook`, `stateWebhook`,
`pollMessageWebhook`, `incomingCallWebhook`, `editedMessageWebhook`, `deletedMessageWebhook`,
`incomingBlockWebhook`, `keepOnlineStatus`, `autoTyping` (Integer), `linkPreview` (Boolean),
`enableLidMode` (Boolean).

## `greenApi.service.*` (class `GreenApiService`)

| Method | Docs | Notes |
|---|---|---|
| `checkWhatsapp(Long phoneNumber)` → `CheckWhatsAppResp` | api/service/CheckWhatsapp/ | bare Long, **not** a chatId; `getExistsWhatsapp()` |
| `getAvatar(String chatId)` → `GetAvatarResp` | api/service/GetAvatar/ | |
| `getContacts()` → `List<GetContactsResp>` | api/service/GetContacts/ | account's contact/chat list |
| `getContactInfo(String chatId)` → `GetContactInfoResp` | api/service/GetContactInfo/ | |
| `deleteMessage(DeleteMessageReq)` / `deleteMessage(MessageReq)` → `String` | api/service/deleteMessage/ | `DeleteMessageReq`: `chatId`, `idMessage`, `onlySenderDelete` (Boolean) |
| `editMessage(EditMessageReq)` → `String` | docs/api/service/EditMessage/ | `chatId`, `idMessage`, `message` (new text) |
| `archiveChat(String chatId)` / `unarchiveChat(String chatId)` → `String` | api/service/archiveChat/ | |
| `sendTyping(SendTypingReq)` → `Void` | docs/api/service/SendTyping/ | `chatId`, `typingTime` (ms), `typingType` (`"typing"`/`"recording"`) |
| `getChats()` / `getChats(Integer count)` → `List<GetChatsResp>` | docs/api/service/GetChats/ | |
| `setDisappearingChat(String chatId, Long ephemeralExpiration)` → `SetDisappearingChatResp` | api/service/SetDisappearingChat/ | seconds: 0 (off), 86400, 604800, 7776000 only |

## `greenApi.marking.*` (class `GreenApiMarking`)

| Method | Docs | Notes |
|---|---|---|
| `readChat(MessageReq)` → `ReadChatResp` | api/marks/ReadChat/ | `chatId` required; `idMessage` null ⇒ mark whole chat read |

## `greenApi.queues.*` (class `GreenApiQueues`)

| Method | Docs | Notes |
|---|---|---|
| `showMessagesQueue()` → `List<QueueMessage>` | api/queues/ShowMessagesQueue/ | messages waiting to be sent |
| `clearMessagesQueue()` → `ClearMessagesQueueResp` | api/queues/ClearMessagesQueue/ | |

Doc URLs are relative to `https://green-api.com/en/` (e.g.
`https://green-api.com/en/docs/api/service/CheckWhatsapp/`).
