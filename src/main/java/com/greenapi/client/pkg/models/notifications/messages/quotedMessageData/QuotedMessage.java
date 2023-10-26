package com.greenapi.client.pkg.models.notifications.messages.quotedMessageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.greenapi.client.pkg.api.webhook.QuotedMessageDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonTypeInfo(
//    use = JsonTypeInfo.Id.NAME,
//    property = "typeMessage"
//)
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = ButtonMessageData.class, name = "buttonMessage"),
//    @JsonSubTypes.Type(value = ContactMessageData.class, name = "contactMessage"),
//    @JsonSubTypes.Type(value = ContactsArrayMessageData.class, name = "contactsArrayMessage"),
//    @JsonSubTypes.Type(value = ExtendedTextMessageData.class, name = "extendedTextMessage"),
//    @JsonSubTypes.Type(value = FileMessageData.class, names = {"imageMessage", "videoMessage","documentMessage", "audioMessage"}),
//    @JsonSubTypes.Type(value = GroupInviteMessageData.class, name = "groupInviteMessage"),
//    @JsonSubTypes.Type(value = ListMessageData.class, name = "listMessage"),
//    @JsonSubTypes.Type(value = LocationMessageData.class, name = "locationMessage"),
//    @JsonSubTypes.Type(value = PollMessageData.class, name = "pollMessage"),
//    @JsonSubTypes.Type(value = ReactionMessageData.class, name = "reactionMessage"),
//    @JsonSubTypes.Type(value = StickerMessageData.class, name = "stickerMessage"),
//    @JsonSubTypes.Type(value = TemplateButtonReplyMessage.class, name = "templateMessage"),
//    @JsonSubTypes.Type(value = TextMessageData.class, name = "textMessage"),
//})
@JsonDeserialize(using = QuotedMessageDeserializer.class)
public abstract class QuotedMessage {
    private String typeMessage;
    private String stanzaId;
    private String participant;
}
