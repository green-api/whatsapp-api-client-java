# Groups, contacts, statuses and journals

All return `ResponseEntity<T>`. Doc URLs relative to `https://green-api.com/en/`.

## `greenApi.groups.*` (class `GreenApiGroups`)

> WARNING (official docs): creating a group with, or inviting, a non-existent number can get
> the sender's number banned by WhatsApp. Group ids end with `@g.us`; participant ids with
> `@c.us`.

| Method | Docs | Request fields |
|---|---|---|
| `createGroup(CreateGroupReq)` → `CreateGroupResp` | api/groups/CreateGroup/ | `groupName`, `chatIds` (`List<String>` of `...@c.us`); response: `isCreated()`, `getChatId()` (new `...@g.us`) |
| `updateGroupName(ChangeGroupNameReq)` → `ChangeGroupNameResp` | api/groups/UpdateGroupName/ | `groupId`, `groupName` |
| `getGroupData(String groupId)` → `GroupData` | api/groups/GetGroupData/ | participants, owner, invite link |
| `addGroupParticipant(ChangeParticipantReq)` → `AddGroupParticipantResp` | api/groups/AddGroupParticipant/ | `groupId`, `participantChatId` |
| `removeGroupParticipant(ChangeParticipantReq)` → `RemoveGroupParticipantResp` | api/groups/RemoveGroupParticipant/ | same |
| `setGroupAdmin(ChangeParticipantReq)` → `SetGroupAdminResp` | api/groups/SetGroupAdmin/ | same |
| `removeGroupAdmin(ChangeParticipantReq)` → `RemoveGroupAdminResp` | api/groups/RemoveAdmin/ | same |
| `setGroupPicture(ChangeGroupPictureReq)` → `SetGroupPictureResp` | api/groups/SetGroupPicture/ | `groupId`, `file` (JPEG `java.io.File`) |
| `updateGroupSettings(UpdateGroupSettingsReq)` → `UpdateGroupSettingsResp` | docs/api/groups/UpdateGroupSettings/ | `groupId`, `allowParticipantsEditGroupSettings` (Boolean), `allowParticipantsSendMessages` (Boolean) |
| `leaveGroup(String groupId)` → `LeaveGroupResp` | api/groups/LeaveGroup/ | |

## `greenApi.contacts.*` (class `GreenApiContacts`)

| Method | Docs | Request fields |
|---|---|---|
| `addContact(AddContactReq)` → `AddContactResp` | docs/api/contacts/AddContact/ | `chatId`, `firstName`, `lastName`, `saveInAddressbook` (Boolean) |
| `editContact(EditContactReq)` → `EditContactResp` | docs/api/contacts/EditContact/ | same fields |
| `deleteContact(DeleteContactReq)` → `DeleteContactResp` | docs/api/contacts/DeleteContact/ | `chatId` |

## `greenApi.statuses.*` (class `GreenApiStatuses`) — WhatsApp Status (stories)

**Request class names are spelled `...Resq` in this SDK** (typo preserved for compatibility).
`participants` (optional `List<String>` of `...@c.us`) limits who sees the status; omitted ⇒
all contacts who have the sender in their address book.

| Method | Docs | Request fields |
|---|---|---|
| `sendTextStatus(SendTextStatusResq)` → `SendMessageResp` | api/statuses/SendTextStatus/ | `message`, `backgroundColor` (hex e.g. `"#87CEEB"`), `font` (`SERIF`, `SANS_SERIF`, `NORICAN_REGULAR`, `BRYNDAN_WRITE`, `OSWALD_HEAVY`), `participants` |
| `sendVoiceStatus(SendVoiceStatusResq)` → `SendMessageResp` | api/statuses/SendVoiceStatus/ | `urlFile`, `fileName` (audio with extension), `backgroundColor`, `participants` |
| `sendMediaStatus(SendMediaStatusResq)` → `SendMessageResp` | api/statuses/SendMediaStatus/ | `urlFile`, `fileName` (image/video with extension), `caption`, `participants` |
| `getIncomingStatuses()` / `(Integer minutes)` → `List<ChatMessage>` | api/statuses/GetIncomingStatuses/ | default last 24 h |
| `getOutgoingStatuses()` / `(Integer minutes)` → `List<ChatMessage>` | api/statuses/GetOutgoingStatuses/ | default last 24 h |
| `getStatusStatistic(String idMessage)` → `List<GetStatusStatisticResp>` | api/statuses/GetStatusStatistic/ | sent/delivered/read recipients |
| `deleteStatus(DeleteStatusReq)` → `Void` | docs/api/statuses/DeleteStatus/ | `idMessage` |

## `greenApi.journals.*` (class `GreenApiJournals`)

| Method | Docs | Notes |
|---|---|---|
| `getChatHistory(GetChatHistoryReq)` → `List<ChatHistoryMessage>` | api/journals/GetChatHistory/ | `chatId`, `count` (default 100) |
| `getMessage(MessageReq)` → `ChatMessage` | api/journals/GetMessage/ | `chatId`, `idMessage` |
| `lastIncomingMessages(Integer minutes)` → `List<ChatMessage>` | api/journals/LastIncomingMessages/ | default 1440 min (24 h) |
| `lastOutgoingMessages(Integer minutes)` → `List<ChatMessage>` | api/journals/LastOutgoingMessages/ | default 24 h |
| `lastIncomingCalls()` / `(Integer minutes)` → `List<CallRecord>` | docs/api/journals/LastIncomingCalls/ | |
| `lastOutgoingCalls()` / `(Integer minutes)` → `List<OutgoingCallRecord>` | docs/api/journals/LastOutgoingCalls/ | |
