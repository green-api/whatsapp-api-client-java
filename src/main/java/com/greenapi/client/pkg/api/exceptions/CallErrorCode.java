package com.greenapi.client.pkg.api.exceptions;

public enum CallErrorCode {
    INVALID_CREDENTIALS("Wrong idInstance or apiTokenInstance"),
    INVALID_CHAT_ID("Invalid chatId for a call, use 79001234567@c.us or 123456789012345@lid"),
    INVALID_REQUEST("GREEN-API rejected the request"),
    CALLS_NOT_ENABLED("Calls are not enabled for this instance"),
    NOT_IN_TARIFF("The instance plan does not include calls"),
    NO_RECENT_CONTACT("Outgoing calls are allowed only to chats that wrote or called in the last 72 hours"),
    INSTANCE_NOT_READY("The instance is not running yet, try again later"),
    ALREADY_IN_CALL("The instance is already in a call"),
    NO_INCOMING_CALL("There is no incoming call to answer"),
    NO_ACTIVE_CALL("There is no active call"),
    CALL_ENDED("The call has already ended"),
    CALL_REFUSED("WhatsApp refused the call"),
    CALL_FAILED("The call could not be completed"),
    TIMEOUT("The calls server did not answer in time"),
    RATE_LIMITED("Too many requests to GREEN-API, try again later"),
    CONNECTION_FAILED("Could not connect to GREEN-API"),
    AUDIO_NOT_STARTED("There is no call audio, start it with startAudioBridge() after dialing or accepting"),
    AUDIO_BUSY("The call audio is already starting or running"),
    AUDIO_FAILED("The call audio could not be connected"),
    INVALID_AUDIO("The audio could not be played"),
    MISSING_DEPENDENCY("Call audio needs the optional dependency dev.onvoid.webrtc:webrtc-java"),
    FFMPEG_NOT_FOUND("ffmpeg was not found. Install ffmpeg, set ffmpegPath, or play a WAV file");

    private final String message;

    CallErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
