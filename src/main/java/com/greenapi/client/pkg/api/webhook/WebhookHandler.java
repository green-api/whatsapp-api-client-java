package com.greenapi.client.pkg.api.webhook;

import com.greenapi.client.pkg.models.notifications.Notification;

public interface WebhookHandler {
    void handle(Notification notification);
}
