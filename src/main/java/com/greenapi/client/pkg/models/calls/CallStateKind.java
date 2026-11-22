package com.greenapi.client.pkg.models.calls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CallStateKind {
    IDLE("idle"),
    INC_CALL("inc-call"),
    OUT_CALL("out-call"),
    ON_CALL("on-call"),
    UNKNOWN("unknown");

    private final String value;

    CallStateKind(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean isActive() {
        return this == INC_CALL || this == OUT_CALL || this == ON_CALL;
    }

    @JsonCreator
    public static CallStateKind fromValue(String value) {
        for (var kind : values()) {
            if (kind.value.equals(value)) {
                return kind;
            }
        }
        return UNKNOWN;
    }
}
