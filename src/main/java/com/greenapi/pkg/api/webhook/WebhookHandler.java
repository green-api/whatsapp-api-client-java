package com.greenapi.pkg.api.webhook;

import com.greenapi.pkg.models.notifications.Notification;

public interface WebhookHandler {
    void handle(Notification notification);
}
