package com.vidsummize.integrations;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DeepSeekIntegrationImpl implements DeepSeekIntegration {

    private final RestTemplate restTemplate;
    private final String ollamaApiUrl;
    private final String ollamaModelName;

    public DeepSeekIntegrationImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();

        this.ollamaApiUrl = System.getenv("OLLAMA_API_URL") != null
                ? System.getenv("OLLAMA_API_URL")
                : "http://localhost:11434/api/chat";

        this.ollamaModelName = System.getenv("OLLAMA_MODEL_NAME") != null
                ? System.getenv("OLLAMA_MODEL_NAME")
                : "deepseek-llm";
    }

    @Override
    public String summarize(String transcription) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject payload = new JSONObject();
            payload.put("model", ollamaModelName);
            payload.put("stream", false);

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "Make a summary of the texts: " + transcription);

            payload.put("messages", List.of(userMessage));

            HttpEntity<String> requestEntity = new HttpEntity<>(payload.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    ollamaApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject json = new JSONObject(response.getBody());
                JSONObject message = json.optJSONObject("message");
                if (message == null) {
                    return "Aviso: sem campo 'message' na resposta -> " + response.getBody();
                }
                String content = message.optString("content", "");
                if (content.isEmpty()) {
                    return "Aviso: campo 'content' vazio na resposta -> " + response.getBody();
                }
                return content.trim();
            } else {
                return "Erro ao resumir o texto: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Erro na comunicação com DeepSeek: " + e.getMessage();
        }
    }
}
