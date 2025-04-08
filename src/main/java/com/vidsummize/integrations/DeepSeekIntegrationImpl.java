package com.vidsummize.integrations;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeepSeekIntegrationImpl implements DeepSeekIntegration {

    private final RestTemplate restTemplate;
    private final String ollamaApiUrl;

    public DeepSeekIntegrationImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();

        this.ollamaApiUrl = System.getenv("OLLAMA_API_URL") != null
                ? System.getenv("OLLAMA_API_URL")
                : "http://localhost:11434/api/chat";
    }

    @Override
    public String summarize(String transcription) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject payload = new JSONObject();
            payload.put("model", "deepseek-llm");
            payload.put("prompt", "Resuma o texto: " + transcription);
            payload.put("stream", false);

            HttpEntity<String> requestEntity = new HttpEntity<>(payload.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    ollamaApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if(response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "Erro ao resumir o texto: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Erro na comunicação com DeepSeek: " + e.getMessage();
        }
    }
}
