package com.greenapi.client.pkg.api.calls;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Locale;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

public final class CallErrors {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CallErrors() {
    }

    public static CallException fromHttp(RestClientResponseException error, String method) {
        var status = error.getStatusCode().value();
        var serverMessage = serverMessage(error.getResponseBodyAsString());
        var code = codeFor(status, serverMessage == null ? "" : serverMessage, method);
        return new CallException(code, code.getMessage(), status, serverMessage, error);
    }

    public static CallException fromHandshake(int status, Throwable cause) {
        var code = status == HTTP_UNAUTHORIZED ? CallErrorCode.INVALID_CREDENTIALS
            : status == HTTP_FORBIDDEN ? CallErrorCode.CALLS_NOT_ENABLED
            : CallErrorCode.CONNECTION_FAILED;
        return new CallException(code, code.getMessage(), status, null, cause);
    }

    public static CallException fromClose(int code, String reason) {
        var notInTariff = code == CLOSE_NOT_IN_TARIFF || reason.toLowerCase(Locale.ROOT).contains(MATCH_NOT_IN_TARIFF);
        var errorCode = notInTariff ? CallErrorCode.NOT_IN_TARIFF : CallErrorCode.CALLS_NOT_ENABLED;
        return new CallException(errorCode, errorCode.getMessage(), code, reason.isEmpty() ? null : reason, null);
    }

    public static boolean isPermanentClose(int code) {
        return code >= PERMANENT_CLOSE_MIN && code <= PERMANENT_CLOSE_MAX;
    }

    public static CallException audio(CallErrorCode code, String detail) {
        return new CallException(code, code.getMessage() + ": " + detail);
    }

    public static CallException wrap(Throwable error) {
        if (error instanceof CallException callException) {
            return callException;
        }
        if (error.getCause() instanceof CallException callException) {
            return callException;
        }
        var text = error.getMessage() == null ? error.getClass().getSimpleName() : error.getMessage();
        return new CallException(CallErrorCode.CALL_FAILED, CallErrorCode.CALL_FAILED.getMessage() + ": " + text, null, null, error);
    }

    public static String describeEndReason(String reason) {
        if (reason == null || reason.isEmpty()) {
            return null;
        }
        var separator = reason.indexOf(':');
        var kind = separator < 0 ? reason : reason.substring(0, separator);
        var description = END_REASON_DESCRIPTIONS.getOrDefault(reason, END_REASON_DESCRIPTIONS.get(kind));
        if (description == null) {
            return reason;
        }
        return separator < 0 ? description : description + ": " + reason.substring(separator + 1);
    }

    private static CallErrorCode codeFor(int status, String serverMessage, String method) {
        var text = serverMessage.toLowerCase(Locale.ROOT);
        switch (status) {
            case 400:
                return "callsDial".equals(method) ? CallErrorCode.INVALID_CHAT_ID : CallErrorCode.INVALID_REQUEST;
            case 401:
                return CallErrorCode.INVALID_CREDENTIALS;
            case 403:
                if (text.contains(MATCH_NO_RECENT_CONTACT)) {
                    return CallErrorCode.NO_RECENT_CONTACT;
                }
                return text.contains(MATCH_NOT_IN_TARIFF) ? CallErrorCode.NOT_IN_TARIFF : CallErrorCode.CALLS_NOT_ENABLED;
            case 409:
                if (text.contains(MATCH_ALREADY_IN_CALL)) {
                    return CallErrorCode.ALREADY_IN_CALL;
                }
                if (text.contains(MATCH_NO_INCOMING_CALL)) {
                    return CallErrorCode.NO_INCOMING_CALL;
                }
                if (text.contains(MATCH_NO_ACTIVE_CALL)) {
                    return CallErrorCode.NO_ACTIVE_CALL;
                }
                return text.contains(MATCH_NOT_INITIALIZED) ? CallErrorCode.INSTANCE_NOT_READY : CallErrorCode.CALL_ENDED;
            case 429:
                return CallErrorCode.RATE_LIMITED;
            case 503:
                return CallErrorCode.CALL_REFUSED;
            case 504:
                return CallErrorCode.TIMEOUT;
            default:
                return CallErrorCode.CALL_FAILED;
        }
    }

    private static String serverMessage(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            JsonNode node = MAPPER.readTree(body);
            if (node.hasNonNull("message")) {
                return node.get("message").asText();
            }
        } catch (Exception ignored) {
            return body;
        }
        return body;
    }
}
