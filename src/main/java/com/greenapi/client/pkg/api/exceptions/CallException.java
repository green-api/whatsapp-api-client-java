package com.greenapi.client.pkg.api.exceptions;

/**
 * Thrown by the calls methods and the calls connection. getCode() tells what went wrong,
 * getServerMessage() keeps the text GREEN-API sent.
 */
public class CallException extends GreenApiClientException {
    private final CallErrorCode code;
    private final Integer statusCode;
    private final String serverMessage;

    public CallException(CallErrorCode code) {
        this(code, code.getMessage(), null, null, null);
    }

    public CallException(CallErrorCode code, String message) {
        this(code, message, null, null, null);
    }

    public CallException(CallErrorCode code, String message, Integer statusCode, String serverMessage, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.statusCode = statusCode;
        this.serverMessage = serverMessage;
    }

    public CallErrorCode getCode() {
        return code;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getServerMessage() {
        return serverMessage;
    }
}
