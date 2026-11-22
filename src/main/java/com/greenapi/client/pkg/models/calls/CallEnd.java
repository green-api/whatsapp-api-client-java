package com.greenapi.client.pkg.models.calls;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * How a call ended. reason is call-ended when GREEN-API ended it, connection-lost when the calls connection dropped.
 * cause is GREEN-API's own word (hangup, timeout, rejected, accepted_elsewhere...), description is readable text.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallEnd {
    private String reason;
    private String cause;
    private String description;
}
