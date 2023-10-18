package com.greenapi.client.pkg.api.exceptions;

public class GreenApiClientException extends RuntimeException {

    public GreenApiClientException() {
        super("Green-api client unknown exception.");
    }

    public GreenApiClientException(String message) {
        super(message);
    }

    public GreenApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
