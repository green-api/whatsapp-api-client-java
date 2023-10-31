package com.greenapi.client.pkg.models.notifications;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@SuperBuilder
public abstract class NotificationBody {
    private String typeWebhook;
}
