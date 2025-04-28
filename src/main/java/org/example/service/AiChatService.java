package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.config.DeepSeekProperties;
import org.example.exception.AiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AiChatService {
    private final RestTemplate restTemplate;
    private final DeepSeekProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(AiChatService.class);

    public String chatCompletion(List<ChatMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getKey());

        ChatRequest request = new ChatRequest(
            properties.getModel(),
            messages,
            properties.getTemperature()
        );

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<ChatResponse> response = restTemplate.exchange(
                properties.getBaseUrl() + properties.getChatEndpoint(),
                HttpMethod.POST,
                entity,
                ChatResponse.class
            );

            return response.getBody().getChoices().get(0).getMessage().getContent();
        } catch (HttpClientErrorException e) {
            logger.error("API请求失败: {}", e.getResponseBodyAsString());
            throw new AiException("AI服务调用失败: " + e.getStatusCode());
        }
    }

    // DTO定义
    @Data
    @AllArgsConstructor
    private static class ChatRequest {
        private String model;
        private List<ChatMessage> messages;
        private double temperature;
    }

    @Data
    public static class ChatResponse {
        private List<Choice> choices;

        @Data
        public static class Choice {
            private Message message;
        }

        @Data
        public static class Message {
            private String content;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChatMessage {
        private String role;
        private String content;
    }
}