package com.greenapi.client.pkg.models.calls;

import com.greenapi.client.pkg.api.calls.CallsDefaults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Options of the calls connection. pcmSampleRate is the rate of sendPcm and onRemotePcm: 8000, 12000, 16000, 24000 or 48000.
 * ffmpegPath is used by play() for audio files that are not WAV.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallsConnectionOptions {
    @Builder.Default
    private int pcmSampleRate = CallsDefaults.DEFAULT_PCM_SAMPLE_RATE;
    @Builder.Default
    private String ffmpegPath = CallsDefaults.DEFAULT_FFMPEG_PATH;
}
