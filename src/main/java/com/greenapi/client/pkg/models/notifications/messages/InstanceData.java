package com.greenapi.client.pkg.models.notifications.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstanceData {
    private Long idInstance;
    private String wid;
    private String typeInstance;
}
