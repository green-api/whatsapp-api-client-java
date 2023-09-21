package com.greenapi.pkg.api.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenapi.pkg.models.notifications.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class WebhookTypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public Notification get(String responseBody) {
        var notification = objectMapper.readTree(responseBody);

        if (notification.has("body") && notification.has("receiptId")) {
            var notificationBody = notification.get("body");
            var receiptId = notification.get("receiptId").asInt();

            switch (notification.get("body").get("typeWebhook").asText()) {

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
        } else {
            log.error("Webhook doesn't have a body and receiptId fields");
        }

        log.error("Webhook unknown type " + responseBody);
        return null;
    }

    @SneakyThrows
    private Notification messageTypeHandle(JsonNode notificationBody, int receiptId) {

        switch (notificationBody.get("messageData").get("typeMessage").asText()) {
            case "textMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), TextMessageReceived.class));
            }
            case "extendedTextMessage", "quotedMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), UrlMessageReceived.class));
            }
            case "imageMessage", "videoMessage", "documentMessage", "audioMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), FileMessageReceived.class));
            }
            case "locationMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), LocationMessageReceived.class));
            }
            case "contactMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ContactMessageReceived.class));
            }
            case "contactsArrayMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ContactsArrayMessageReceived.class));
            }
            case "buttonsMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ButtonMessageReceived.class));
            }
            case "listMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ListMessageReceived.class));
            }
            case "templateButtonReplyMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), TemplateButtonSelectionMessageReceived.class));
            }
            case "buttonsResponseMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), SimpleButtonSelectionMessageReceived.class));
            }
            case "listResponseMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ListSelectionMessageReceived.class));
            }
            case "stickerMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), StickerMessageReceived.class));
            }
            case "reactionMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), ReactionMessageReceived.class));
            }
            case "groupInviteMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), GroupInviteMessageReceived.class));
            }
            case "pollCreationMessage" -> {
                return new Notification(receiptId, objectMapper.readValue(notificationBody.toString(), PollMessageReceived.class));
            }
        }

        log.error("Message data unknown type " + notificationBody);
        return null;
    }
}
