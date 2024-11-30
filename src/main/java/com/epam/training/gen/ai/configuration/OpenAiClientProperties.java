package com.epam.training.gen.ai.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "client.openai")
public class OpenAiClientProperties {
    String key;
    String endpoint;
    String deploymentName;
}
