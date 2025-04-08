package com.vidsummize.integrations;

import org.springframework.stereotype.Component;

@Component
public class DeepSeekIntegrationImpl implements DeepSeekIntegration {
    @Override
    public String summarize(String transcription) {
        int maxLength = 50;
        String simSummary = transcription.length() > maxLength
                ? transcription.substring(0, maxLength) + "..."
                : transcription;
        return "Resumo simulado: " + simSummary;
    }
}