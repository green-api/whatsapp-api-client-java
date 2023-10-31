package com.greenapi.client.pkg.api.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.greenapi.client.pkg.api.exceptions.GreenApiClientException;
import com.greenapi.client.pkg.models.notifications.*;
import com.greenapi.client.pkg.models.notifications.messages.quotedMessageData.QuotedMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationMapper {

    private final ObjectMapper objectMapper;

    public NotificationMapper() {
        this.objectMapper = new ObjectMapper();

        var module = new SimpleModule();
        module.addDeserializer(QuotedMessage.class, new QuotedMessageDeserializer());
        objectMapper.registerModule(module);
    }

    public Notification get(String responseBody) {
        int receiptId = -1;

        try {
            var notification = objectMapper.readTree(responseBody);

            if (notification.has("body") && notification.has("receiptId")) {
                receiptId = notification.get("receiptId").asInt();
                return webhookTypeHandle(notification.get("body"), receiptId);

            } else if (notification.has("typeWebhook")) {
                return webhookTypeHandle(notification, null);
            }

        } catch (JsonProcessingException e) {
            new GreenApiClientException("Webhook unknown type, please write to green-api support " + responseBody, e).printStackTrace();
        }

        return new Notification(receiptId, null);
    }

    private Notification webhookTypeHandle(JsonNode notificationBody, Integer receiptId) throws JsonProcessingException {

        switch (notificationBody.get("typeWebhook").asText()) {
            case "incomingMessageReceived", "outgoingMessageReceived", "outgoingAPIMessageReceived" -> {
                return messageTypeHandle(notificationBody, receiptId);
            }
            case "outgoingMessageStatus" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), OutgoingMessageStatus.class));
            }
            case "stateInstanceChanged" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), StatusInstanceChanged.class));
            }
            case "statusInstanceChanged" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), StateInstanceChanged.class));
            }
            case "deviceInfo" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), DeviceInfo.class));
            }
            case "incomingCall" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), IncomingCall.class));
            }
            case "incomingBlock" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), IncomingBlock.class));
            }
        }

        throw new GreenApiClientException("Unknown notification type " + notificationBody);
    }

    private Notification messageTypeHandle(JsonNode notificationBody, Integer receiptId) throws JsonProcessingException {
        var typeMessage = notificationBody.get("messageData").get("typeMessage").asText();

        return new Notification(receiptId, (NotificationBody) objectMapper.readValue(
            notificationBody.toString(), getNotificationClass(typeMessage)));
    }

    private Class<?> getNotificationClass(String typeMessage) {
        switch (typeMessage) {
            case "quotedMessage" -> {
                return QuotedMessageWebhook.class;
            }
            case "textMessage" -> {
                return TextMessageWebhook.class;
            }
            case "extendedTextMessage" -> {
                return UrlMessageWebhook.class;
            }
            case "imageMessage", "videoMessage", "documentMessage", "audioMessage" -> {
                return FileMessageWebhook.class;
            }
            case "locationMessage" -> {
                return LocationMessageWebhook.class;
            }
            case "contactMessage" -> {
                return ContactMessageWebhook.class;
            }
            case "contactsArrayMessage" -> {
                return ContactsArrayMessageWebhook.class;
            }
            case "buttonsMessage" -> {
                return ButtonMessageWebhook.class;
            }
            case "listMessage" -> {
                return ListMessageWebhook.class;
            }
            case "templateButtonReplyMessage" -> {
                return TemplateButtonSelectionMessageWebhook.class;
            }
            case "buttonsResponseMessage" -> {
                return SimpleButtonSelectionMessageWebhook.class;
            }
            case "listResponseMessage" -> {
                return ListSelectionMessageWebhook.class;
            }
            case "stickerMessage" -> {
                return StickerMessageWebhook.class;
            }
            case "reactionMessage" -> {
                return ReactionMessageWebhook.class;
            }
            case "groupInviteMessage" -> {
                return GroupInviteMessageWebhook.class;
            }
            case "pollCreationMessage" -> {
                return PollMessageWebhook.class;
            }
            default -> throw new GreenApiClientException("Message data unknown type " + typeMessage);
        }
    }
}