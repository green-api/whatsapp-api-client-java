package com.greenapi.client.pkg.api.webhook;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.*;

import java.io.IOException;

public class QuotedMessageDeserializer extends StdDeserializer<QuotedMessage> {

    public QuotedMessageDeserializer() {
        this(null);
    }

    public QuotedMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public QuotedMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("typeMessage")) {
            switch (node.get("typeMessage").toString()) {
                case "buttonMessage" -> {
                    return ctxt.readValue(jp, ButtonMessageData.class);
                }
                case "contactMessage" -> {
                    return ctxt.readValue(jp, ContactMessageData.class);
                }
                case "contactsArrayMessage" -> {
                    return ctxt.readValue(jp, ContactsArrayMessageData.class);
                }
                case "extendedTextMessage" -> {
                    return ctxt.readValue(jp, ExtendedTextMessageData.class);
                }
                case "imageMessage", "videoMessage", "documentMessage", "audioMessage" -> {
                    return ctxt.readValue(jp, FileMessageData.class);
                }
                case "groupInviteMessage" -> {
                    return ctxt.readValue(jp, GroupInviteMessageData.class);
                }
                case "listMessage" -> {
                    return ctxt.readValue(jp, ListMessageData.class);
                }
                case "locationMessage" -> {
                    return ctxt.readValue(jp, LocationMessageData.class);
                }
                case "pollMessage" -> {
                    return ctxt.readValue(jp, PollMessageData.class);
                }
                case "reactionMessage" -> {
                    return ctxt.readValue(jp, ReactionMessageData.class);
                }
                case "stickerMessage" -> {
                    return ctxt.readValue(jp, StickerMessageData.class);
                }
                case "templateMessage" -> {
                    return ctxt.readValue(jp, TemplateMessageData.class);
                }
                case "textMessage" -> {
                    return ctxt.readValue(jp, TextMessageData.class);
                }
                case "templateButtonsReplyMessage" -> {
                    return ctxt.readValue(jp, TemplateButtonReplyMessageData.class);
                }
            }
        }
        return new EmptyQuotedMessage();
    }
}
