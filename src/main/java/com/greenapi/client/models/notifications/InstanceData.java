package com.greenapi.client.models.notifications;

import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class InstanceData {
    private final Long idInstance;
    private final String wid;
    private final String typeInstance;
}
