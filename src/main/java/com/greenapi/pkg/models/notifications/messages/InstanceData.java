package com.greenapi.pkg.models.notifications.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstanceData {
    private Long idInstance;
    private String wid;
    private String typeInstance;
}
