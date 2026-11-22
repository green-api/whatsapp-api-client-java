package com.greenapi.client.pkg.api.calls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.greenapi.client.pkg.api.exceptions.CallErrorCode;
import com.greenapi.client.pkg.api.exceptions.CallException;
import com.greenapi.client.pkg.models.response.IceServer;
import dev.onvoid.webrtc.*;
import dev.onvoid.webrtc.media.audio.AudioTrack;
import dev.onvoid.webrtc.media.audio.AudioTrackSink;
import dev.onvoid.webrtc.media.audio.CustomAudioSource;
import dev.onvoid.webrtc.media.audio.HeadlessAudioDeviceModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.greenapi.client.pkg.api.calls.CallsDefaults.*;

class AudioBridge {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Engine engine;
    private final Executor events;
    private final ScheduledExecutorService timers;
    private final Consumer<String> send;
    private final BiConsumer<short[], Integer> onRemoteAudio;
    private final Consumer<CallException> onFailed;
    private final RTCPeerConnection peer;
    private final CustomAudioSource source;
    private final RTCRtpSender sender;
    private final PcmSender pcmSender;
    private final CompletableFuture<Void> connected = new CompletableFuture<>();
    private final CompletableFuture<Void> remoteAudio = new CompletableFuture<>();
    private final List<RTCIceCandidate> earlyCandidates = new ArrayList<>();
    private final List<RTCIceCandidate> pendingRemote = new ArrayList<>();
    private AudioTrack remoteTrack;
    private AudioTrackSink sink;
    private boolean offerSent;
    private boolean answered;
    private boolean remoteDescriptionSet;
    private boolean closed;

    static final class Engine {
        private final HeadlessAudioDeviceModule module;
        private final PeerConnectionFactory factory;

        private Engine() {
            module = new HeadlessAudioDeviceModule();
            module.initPlayout();
            module.startPlayout();
            factory = new PeerConnectionFactory(module);
        }

        static Engine create() {
            try {
                return new Engine();
            } catch (NoClassDefFoundError e) {
                throw new CallException(CallErrorCode.MISSING_DEPENDENCY, CallErrorCode.MISSING_DEPENDENCY.getMessage(), null, null, e);
            } catch (UnsatisfiedLinkError e) {
                throw new CallException(CallErrorCode.AUDIO_FAILED,
                    CallErrorCode.AUDIO_FAILED.getMessage() + ": the WebRTC native library could not be loaded", null, null, e);
            }
        }

        void dispose() {
            module.stopPlayout();
            module.dispose();
            factory.dispose();
        }
    }

    AudioBridge(Engine engine, List<IceServer> iceServers, Executor events, ScheduledExecutorService timers,
                Consumer<String> send, BiConsumer<short[], Integer> onRemoteAudio, Consumer<CallException> onFailed) {
        this.engine = engine;
        this.events = events;
        this.timers = timers;
        this.send = send;
        this.onRemoteAudio = onRemoteAudio;
        this.onFailed = onFailed;
        var config = new RTCConfiguration();
        for (var server : iceServers) {
            var iceServer = new RTCIceServer();
            if (server.getUrls() != null) {
                iceServer.urls.addAll(server.getUrls());
            }
            iceServer.username = server.getUsername();
            iceServer.password = server.getCredential();
            config.iceServers.add(iceServer);
        }
        peer = engine.factory.createPeerConnection(config, new Observer());
        source = new CustomAudioSource();
        var track = engine.factory.createAudioTrack(AUDIO_TRACK_ID, source);
        sender = peer.addTrack(track, List.of(AUDIO_STREAM_ID));
        limitBitrate();
        pcmSender = new PcmSender(frame -> source.pushAudio(frame, AUDIO_BITS, AUDIO_SAMPLE_RATE, AUDIO_CHANNELS, FRAME_SAMPLES));
    }

    CompletableFuture<Void> connected() {
        return connected;
    }

    CompletableFuture<Void> remoteAudio() {
        return remoteAudio;
    }

    PcmSender pcm() {
        return pcmSender;
    }

    void start() {
        peer.createOffer(new RTCOfferOptions(), new CreateSessionDescriptionObserver() {
            @Override
            public void onSuccess(RTCSessionDescription offer) {
                peer.setLocalDescription(offer, new SetSessionDescriptionObserver() {
                    @Override
                    public void onSuccess() {
                        timers.schedule(() -> events.execute(AudioBridge.this::sendOffer), GATHER_WAIT.toMillis(), TimeUnit.MILLISECONDS);
                    }

                    @Override
                    public void onFailure(String error) {
                        events.execute(() -> fail(error));
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                events.execute(() -> fail(error));
            }
        });
    }

    boolean setAnswer(String sdp) {
        if (answered || closed) {
            return false;
        }
        answered = true;
        var applied = new CompletableFuture<Void>();
        peer.setRemoteDescription(new RTCSessionDescription(RTCSdpType.ANSWER, sdp), new SetSessionDescriptionObserver() {
            @Override
            public void onSuccess() {
                applied.complete(null);
            }

            @Override
            public void onFailure(String error) {
                applied.completeExceptionally(CallErrors.audio(CallErrorCode.AUDIO_FAILED, error));
            }
        });
        applied.join();
        remoteDescriptionSet = true;
        pendingRemote.forEach(peer::addIceCandidate);
        pendingRemote.clear();
        return true;
    }

    void addCandidate(String candidate, String sdpMid, int sdpMLineIndex) {
        if (closed) {
            return;
        }
        var iceCandidate = new RTCIceCandidate(sdpMid, sdpMLineIndex, candidate);
        if (!remoteDescriptionSet) {
            pendingRemote.add(iceCandidate);
            return;
        }
        peer.addIceCandidate(iceCandidate);
    }

    void close() {
        if (closed) {
            return;
        }
        closed = true;
        pcmSender.stop();
        var stopped = CallErrors.audio(CallErrorCode.AUDIO_FAILED, DETAIL_STOPPED);
        connected.completeExceptionally(stopped);
        remoteAudio.completeExceptionally(stopped);
        if (remoteTrack != null && sink != null) {
            remoteTrack.removeSink(sink);
        }
        peer.close();
        sender.dispose();
        source.dispose();
    }

    private void sendOffer() {
        if (closed || offerSent) {
            return;
        }
        var description = peer.getLocalDescription();
        if (description == null) {
            return;
        }
        var frame = MAPPER.createObjectNode().put("type", "offer");
        frame.putObject("offer").put("type", "offer").put("sdp", description.sdp);
        send.accept(frame.toString());
        offerSent = true;
        var offered = new HashSet<String>();
        description.sdp.lines().filter(line -> line.startsWith("a=candidate:")).forEach(line -> offered.add(line.substring(2)));
        earlyCandidates.stream().filter(candidate -> !offered.contains(candidate.sdp)).forEach(this::sendCandidate);
        earlyCandidates.clear();
    }

    private void sendCandidate(RTCIceCandidate candidate) {
        var frame = MAPPER.createObjectNode().put("type", "ice-candidate");
        ObjectNode body = frame.putObject("candidate");
        body.put("candidate", candidate.sdp).put("sdpMid", candidate.sdpMid).put("sdpMLineIndex", candidate.sdpMLineIndex);
        send.accept(frame.toString());
    }

    private void limitBitrate() {
        var parameters = sender.getParameters();
        if (parameters.encodings == null || parameters.encodings.isEmpty()) {
            return;
        }
        parameters.encodings.forEach(encoding -> encoding.minBitrate = MIN_SEND_BITRATE);
        try {
            sender.setParameters(parameters);
        } catch (RuntimeException ignored) {
        }
    }

    private void fail(String detail) {
        if (closed) {
            return;
        }
        var error = CallErrors.audio(CallErrorCode.AUDIO_FAILED, detail);
        connected.completeExceptionally(error);
        onFailed.accept(error);
    }

    private class Observer implements PeerConnectionObserver {
        @Override
        public void onIceCandidate(RTCIceCandidate candidate) {
            events.execute(() -> {
                if (closed) {
                    return;
                }
                if (offerSent) {
                    sendCandidate(candidate);
                } else {
                    earlyCandidates.add(candidate);
                }
            });
        }

        @Override
        public void onIceGatheringChange(RTCIceGatheringState state) {
            if (state == RTCIceGatheringState.COMPLETE) {
                events.execute(AudioBridge.this::sendOffer);
            }
        }

        @Override
        public void onConnectionChange(RTCPeerConnectionState state) {
            if (state == RTCPeerConnectionState.CONNECTED) {
                connected.complete(null);
            } else if (state == RTCPeerConnectionState.FAILED) {
                events.execute(() -> fail(DETAIL_CONNECTION_FAILED));
            }
        }

        @Override
        public void onTrack(RTCRtpTransceiver transceiver) {
            var track = transceiver.getReceiver().getTrack();
            if (!(track instanceof AudioTrack audioTrack)) {
                return;
            }
            sink = new AudioTrackSink() {
                @Override
                public void onData(byte[] data, int bitsPerSample, int sampleRate, int channels, int frames) {
                    deliver(data, sampleRate, channels, frames, false);
                }

                @Override
                public void onData(byte[] data, int bitsPerSample, int sampleRate, int channels, int frames, RTCRtpPacketInfo[] packetInfos) {
                    deliver(data, sampleRate, channels, frames, packetInfos != null && packetInfos.length > 0);
                }
            };
            remoteTrack = audioTrack;
            audioTrack.addSink(sink);
        }

        private void deliver(byte[] data, int sampleRate, int channels, int frames, boolean fromNetwork) {
            if (closed) {
                return;
            }
            if (fromNetwork) {
                remoteAudio.complete(null);
            }
            var samples = Resampler.toSamples(data, Math.min(data.length, frames * channels * BYTES_PER_SAMPLE));
            onRemoteAudio.accept(Resampler.toMono(samples, channels), sampleRate);
        }
    }
}
