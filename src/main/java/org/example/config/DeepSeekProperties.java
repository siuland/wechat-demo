package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "deepseek.api")
@Validated
public class DeepSeekProperties {
    @NotBlank
    private String key;
    @NotBlank private String baseUrl;
    @NotBlank private String chatEndpoint;
    @Min(1000) private int timeout = 5000;
    @Min(0) @Max(5) private int maxRetries = 3;
    @NotBlank private String model;
    @NotNull
    private double temperature;
}