package com.project.pet_veteriana.bl;

import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class GeminiBl {
    private static final Logger logger = LoggerFactory.getLogger(GeminiBl.class);
    
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    
    private final String apiKey;
    private final String model;
    private final OkHttpClient client;

    public GeminiBl(@Value("${gemini.api.key}") String apiKey,
                   @Value("${gemini.model:gemini-1.5-flash-latest}") String model) {
        this.apiKey = Objects.requireNonNull(apiKey, "API key no puede ser nula");
        this.model = Objects.requireNonNull(model, "Model no puede ser nulo");
        
        this.client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
        
        logger.info("GeminiService inicializado con modelo: {}", model);
    }

    public String generateContent(String prompt) throws IOException {
        validatePrompt(prompt);
        
        Request request = buildRequest(prompt);
        
        try (Response response = client.newCall(request).execute()) {
            return processResponse(response);
        }
    }

    private Request buildRequest(String prompt) {
        JSONObject requestBody = new JSONObject()
            .put("contents", new JSONObject[]{
                new JSONObject().put("parts", new JSONObject[]{
                    new JSONObject().put("text", prompt)
                })
            });
        
        return new Request.Builder()
            .url(String.format("%s/models/%s:generateContent?key=%s", BASE_URL, model, apiKey))
            .post(RequestBody.create(requestBody.toString(), JSON_MEDIA_TYPE))
            .header("Content-Type", "application/json")
            .build();
    }

    private String processResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            String errorBody = response.body() != null ? response.body().string() : "No hay detalles del error";
            logger.error("Error en API Gemini - Código: {} - Respuesta: {}", response.code(), errorBody);
            throw new GeminiApiException(response.code(), "Error en API Gemini: " + errorBody);
        }
        
        String responseBody = Objects.requireNonNull(response.body()).string();
        return new JSONObject(responseBody)
            .getJSONArray("candidates")
            .getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")
            .getJSONObject(0)
            .getString("text");
    }

    private void validatePrompt(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException("El prompt no puede estar vacío");
        }
    }

    public static class GeminiApiException extends IOException {
        private final int statusCode;

        public GeminiApiException(int statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}