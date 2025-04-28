package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(DeepSeekProperties.class)
public class AiConfig {
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, DeepSeekProperties properties) {
        return builder
            .setConnectTimeout(Duration.ofMillis(properties.getTimeout()))
            .setReadTimeout(Duration.ofMillis(properties.getTimeout()))
            .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    }
}