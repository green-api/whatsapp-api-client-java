# Sending methods — `greenApi.sending.*`

Class: `com.greenapi.client.pkg.api.methods.GreenApiSending`.
All request DTOs live in `com.greenapi.client.pkg.models.request`, responses in
`...models.response`. All methods return `ResponseEntity<T>`.

Chat-targeted DTOs extending `Outgoing` share two builder fields: `chatId` (required) and
`quotedMessageId` (optional — id of a message to quote/reply to).

## sendMessage(OutgoingMessage) → SendMessageResp
Text message to a personal or group chat. Docs: https://green-api.com/en/docs/api/sending/SendMessage/
`OutgoingMessage` builder fields:
- `chatId` (required) — `"79876543210@c.us"` / `"...@g.us"`
- `message` (required) — text, max 20000 chars, UTF-8, emoji OK
- `quotedMessageId`, `linkPreview` (Boolean, default true), `typePreview` (`"large"`/`"small"`),
  `customPreview` (`CustomPreview`: title, description, link, urlFile, jpegThumbnail),
  `typingTime` (Integer, 1000–20000 ms — shows typing indicator first)

Response: `SendMessageResp.getIdMessage()`.

## sendFileByUrl(OutgoingFileByUrl) → SendMessageResp
Docs: https://green-api.com/en/docs/api/sending/SendFileByUrl/
Fields: `chatId`, `urlFile` (direct http(s) link to the file, no spaces/special chars),
`fileName` (**must contain the extension** — drives file-type detection), `caption`
(max 1024 chars), `quotedMessageId`, `typingTime`, `typingType` (`"typing"`/`"recording"`).
Max file size 100 MB. Sending a file may take 1–20 s.

## sendFileByUpload(OutgoingFileByUpload) → SendFileByUploadResp
Docs: https://green-api.com/en/docs/api/sending/SendFileByUpload/
Same as above but with `file` (`java.io.File`) instead of `urlFile`. Uses the media host.

## uploadFile(File) → UploadFileResp
Docs: https://green-api.com/en/docs/api/sending/UploadFile/
Uploads to GREEN-API storage; throws `IOException`. Use `getBody().getUrlFile()` with
`sendFileByUrl`.

## sendLocation(OutgoingLocation) → SendMessageResp
Docs: https://green-api.com/en/docs/api/sending/SendLocation/
Fields: `chatId`, `latitude` (Double, required), `longitude` (Double, required),
`nameLocation`, `address`, `quotedMessageId`.

## sendContact(OutgoingContact) → SendMessageResp
Docs: https://green-api.com/en/docs/api/sending/SendContact/
Fields: `chatId`, `contact` (`com.greenapi.client.pkg.models.Contact` builder:
`phoneContact` (Long), `firstName`, `middleName`, `lastName`, `company`), `quotedMessageId`.

## sendPoll(OutgoingPoll) → SendMessageResp
Docs: https://green-api.com/en/docs/api/sending/SendPoll/
Fields: `chatId`, `message` (poll question), `options`
(`List<com.greenapi.client.pkg.models.Option>`, `new Option("text")`, 2–12 unique options),
`multipleAnswers` (Boolean, default false), `quotedMessageId`.

## forwardMessages(ForwardMessagesReq) → ForwardMessagesResp
Docs: https://green-api.com/en/docs/api/sending/ForwardMessages/
Fields: `chatId` (destination), `chatIdFrom` (source chat), `messages`
(`List<String>` of message ids), `typingTime`.

## sendInteractiveButtons(OutgoingInteractiveButtons) → SendMessageResp
Docs: https://green-api.com/docs/api/sending/SendInteractiveButtons/
Fields: `chatId`, `header`, `body`, `footer`, `buttons` (`List<InteractiveButton>`).
`InteractiveButton` builder: `type` (`"copy"`/`"call"`/`"url"`), `buttonId`, `buttonText`,
plus one of `copyCode` / `phoneNumber` / `url` matching the type.

## sendInteractiveButtonsReply(OutgoingInteractiveButtonsReply) → SendMessageResp
Docs: https://green-api.com/docs/api/sending/SendInteractiveButtonsReply/ (beta)
Fields: `chatId`, `header`, `body`, `footer`, `buttons` (`List<InteractiveReplyButton>`:
`buttonId`, `buttonText`).

## sendButtons(OutgoingButtons), sendTemplateButtons(OutgoingTemplateButtons), sendListMessage(OutgoingListMessage) → SendMessageResp
Legacy/limited-support button messages (WhatsApp has deprecated classic buttons; prefer
`sendInteractiveButtons`).
- `OutgoingButtons`: `chatId`, `message`, `footer`, `buttons` (`List<Button>`: `buttonId`
  (Integer), `buttonText`).
- `OutgoingTemplateButtons`: `chatId`, `message`, `footer`, `templateButtons`
  (`List<TemplateButtons>` with `index` and one of `urlButton` (`UrlButton`),
  `callButton` (`CallButton`), `quickReplyButton` (`QuickReplyButton`)).
- `OutgoingListMessage`: `chatId`, `message`, `title`, `footer`, `buttonText`, `sections`
  (`List<OutgoingListMessage.Section>`; `Section`: `title`, `rows`
  (`List<OutgoingListMessage.Row>`: `rowId`, `title`, `description`)).
