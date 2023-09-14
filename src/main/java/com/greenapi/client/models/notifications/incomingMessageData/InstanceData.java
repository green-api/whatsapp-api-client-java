package com.greenapi.client.models.notifications.incomingMessageData;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class InstanceData {
    private final Long idInstance;
    private final String wid;
    private final String typeInstance;
}
