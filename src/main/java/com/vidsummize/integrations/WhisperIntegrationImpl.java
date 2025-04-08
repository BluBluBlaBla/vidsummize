package com.vidsummize.integrations;

import org.springframework.stereotype.Component;

@Component
public class WhisperIntegrationImpl implements WhisperIntegration {
    @Override
    public String transcribe(String youtubeUrl) {
        return "Transcrição simulada para o vídeo " + youtubeUrl;
    }
}