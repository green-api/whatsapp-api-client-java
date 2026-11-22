package com.greenapi.client.pkg.api.calls;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public final class CallsDefaults {
    public static final String CALLS_RTC_ENDPOINT = "callsRtc";
    public static final int CLOSE_NORMAL = 1000;
    public static final int CLOSE_ABNORMAL = 1006;
    public static final int PERMANENT_CLOSE_MIN = 4000;
    public static final int PERMANENT_CLOSE_MAX = 4999;
    public static final int CLOSE_NOT_IN_TARIFF = 4403;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final Duration RECONNECT_FIRST_DELAY = Duration.ofMillis(500);
    public static final Duration RECONNECT_MAX_DELAY = Duration.ofSeconds(10);
    public static final Duration HEARTBEAT_INTERVAL = Duration.ofSeconds(20);
    public static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration OPEN_TIMEOUT = Duration.ofSeconds(20);
    public static final Duration ANSWER_TIMEOUT = Duration.ofSeconds(30);

    public static final String MATCH_NO_RECENT_CONTACT = "72 hours";
    public static final String MATCH_NOT_IN_TARIFF = "tariff";
    public static final String MATCH_ALREADY_IN_CALL = "already in a call";
    public static final String MATCH_NO_INCOMING_CALL = "no incoming call";
    public static final String MATCH_NO_ACTIVE_CALL = "no active call";
    public static final String MATCH_NOT_INITIALIZED = "not initialized";

    public static final String END_REASON_CALL_ENDED = "call-ended";
    public static final String END_REASON_CONNECTION_LOST = "connection-lost";
    public static final String CALL_ENDED_DESCRIPTION = "Call ended";
    public static final String CONNECTION_LOST_DESCRIPTION = "The calls connection was lost";
    public static final Map<String, String> END_REASON_DESCRIPTIONS = Map.of(
        "hungUp", "Call ended",
        "hangup", "Call ended",
        "timeout", "No answer",
        "rejected", "Call declined",
        "accepted_elsewhere", "Answered on another device",
        "rejected_elsewhere", "Declined on another device",
        "no-media", "No audio was transmitted",
        "connect-timeout", "The audio connection could not be established",
        "instance-gone", "The instance became unavailable"
    );

    public static final List<Integer> PCM_SAMPLE_RATES = List.of(8000, 12000, 16000, 24000, 48000);
    public static final int DEFAULT_PCM_SAMPLE_RATE = 24000;
    public static final String DEFAULT_FFMPEG_PATH = "ffmpeg";
    public static final int AUDIO_SAMPLE_RATE = 48000;
    public static final int AUDIO_BITS = 16;
    public static final int AUDIO_CHANNELS = 1;
    public static final int FRAME_MS = 10;
    public static final int FRAME_SAMPLES = AUDIO_SAMPLE_RATE / 1000 * FRAME_MS;
    public static final int BYTES_PER_SAMPLE = 2;
    public static final int SAMPLES_PER_MS = AUDIO_SAMPLE_RATE / 1000;
    public static final Duration MAX_SEND_LAG = Duration.ofMillis(200);
    public static final Duration STOP_WAIT = Duration.ofMillis(500);
    public static final Duration GATHER_WAIT = Duration.ofSeconds(1);
    public static final Duration AUDIO_CONNECT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration REMOTE_AUDIO_WAIT = Duration.ofSeconds(3);
    public static final int MIN_SEND_BITRATE = 32000;
    public static final int MAX_SOURCE_BYTES = 50 * 1024 * 1024;
    public static final String AUDIO_TRACK_ID = "green-api-audio";
    public static final String AUDIO_STREAM_ID = "green-api-stream";
    public static final List<String> FFMPEG_ARGS = List.of(
        "-hide_banner", "-loglevel", "error", "-i", "pipe:0",
        "-vn", "-ac", "1", "-ar", String.valueOf(AUDIO_SAMPLE_RATE), "-f", "s16le", "pipe:1"
    );

    public static final String DETAIL_STOPPED = "the call audio was stopped";
    public static final String DETAIL_CALL_ENDED = "the call ended before the audio connected";
    public static final String DETAIL_CONNECTION_LOST = "the calls connection was lost while the audio was connecting";
    public static final String DETAIL_NOT_CONNECTED = "the audio connection was not established in time";
    public static final String DETAIL_CONNECTION_FAILED = "the audio connection failed";
    public static final String DETAIL_NO_ANSWER = "GREEN-API did not answer the audio offer in time";
    public static final String DETAIL_SAMPLE_RATE = "pcmSampleRate must be one of 8000, 12000, 16000, 24000, 48000";
    public static final String DETAIL_TOO_LARGE = "the audio is larger than 50 MB";
    public static final String DETAIL_BAD_WAV = "the WAV file is not 16-bit PCM";
    public static final String DETAIL_FFMPEG = "ffmpeg could not convert the audio";

    private CallsDefaults() {
    }
}
