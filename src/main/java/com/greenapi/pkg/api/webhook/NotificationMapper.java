package com.greenapi.pkg.api.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenapi.pkg.models.notifications.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class NotificationMapper {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public Notification get(String responseBody) {
        var notification = objectMapper.readTree(responseBody);

        if (notification.has("body") && notification.has("receiptId")) {
            return notificationTypeHandle(notification.get("body"), notification.get("receiptId").asInt());

        } else if (notification.has("typeWebhook")) {
            return notificationTypeHandle(notification, null);
        }

        log.error("Webhook unknown type " + responseBody);
        return null;
    }

    private Notification notificationTypeHandle(JsonNode notificationBody, Integer receiptId) throws JsonProcessingException {

        switch (notificationBody.get("typeWebhook").asText()) {
            case "incomingMessageReceived", "outgoingMessageReceived", "outgoingAPIMessageReceived" -> {
                return messageTypeHandle(notificationBody, receiptId);
            }
            case "outgoingMessageStatus" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), OutgoingMessageStatus.class));
            }
            case "stateInstanceChanged" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), AccountStatus.class));
            }
            case "statusInstanceChanged" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), AccountSocketStatus.class));
            }
            case "deviceInfo" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), DeviceStatus.class));
            }
            case "incomingCall" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), IncomingCall.class));
            }
        }

        log.error("Unknown notification type " + notificationBody.toString());
        return null;
    }

    private Notification messageTypeHandle(JsonNode notificationBody, Integer receiptId) throws JsonProcessingException {
        var typeMessage = notificationBody.get("messageData").get("typeMessage").asText();

        return new Notification(receiptId, (NotificationBody) objectMapper.readValue(
            notificationBody.toString(), getNotificationClass(typeMessage)));
    }

    private Class<?> getNotificationClass(String typeMessage) {
        switch (typeMessage) {
            case "quotedMessage" -> {
                return QuotedMessageReceived.class;
            }
            case "textMessage" -> {
                return TextMessageReceived.class;
            }
            case "extendedTextMessage" -> {
                return UrlMessageReceived.class;
            }
            case "imageMessage", "videoMessage", "documentMessage", "audioMessage" -> {
                return FileMessageReceived.class;
            }
            case "locationMessage" -> {
                return LocationMessageReceived.class;
            }
            case "contactMessage" -> {
                return ContactMessageReceived.class;
            }
            case "contactsArrayMessage" -> {
                return ContactsArrayMessageReceived.class;
            }
            case "buttonsMessage" -> {
                return ButtonMessageReceived.class;
            }
            case "listMessage" -> {
                return ListMessageReceived.class;
            }
            case "templateButtonReplyMessage" -> {
                return TemplateButtonSelectionMessageReceived.class;
            }
            case "buttonsResponseMessage" -> {
                return SimpleButtonSelectionMessageReceived.class;
            }
            case "listResponseMessage" -> {
                return ListSelectionMessageReceived.class;
            }
            case "stickerMessage" -> {
                return StickerMessageReceived.class;
            }
            case "reactionMessage" -> {
                return ReactionMessageReceived.class;
            }
            case "groupInviteMessage" -> {
                return GroupInviteMessageReceived.class;
            }
            case "pollCreationMessage" -> {
                return PollMessageReceived.class;
            }
        }

        log.error("Message data unknown type " + typeMessage);
        return null;
    }
}