package com.greenapi.client.pkg.models.calls;

import com.greenapi.client.pkg.api.exceptions.CallException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Why the calls connection dropped. permanent means GREEN-API refused it and it will not reconnect.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallsDisconnect {
    private String reason;
    private Integer code;
    private boolean permanent;
    private CallException error;
}
