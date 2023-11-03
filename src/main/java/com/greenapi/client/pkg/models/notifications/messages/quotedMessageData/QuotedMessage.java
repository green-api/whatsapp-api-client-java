package com.greenapi.client.pkg.models.notifications.messages.quotedMessageData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.greenapi.client.pkg.api.webhook.QuotedMessageDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = QuotedMessageDeserializer.class)
public abstract class QuotedMessage {
    private String typeMessage;
    private String stanzaId;
    private String participant;
}
