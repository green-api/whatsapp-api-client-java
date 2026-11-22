package com.greenapi.client.pkg.models.calls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Call state of the instance. reason comes only with the idle state that ends a call, and only on the calls connection.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallState {
    private CallStateKind state;
    private CallInfo info;
    private String reason;
}
