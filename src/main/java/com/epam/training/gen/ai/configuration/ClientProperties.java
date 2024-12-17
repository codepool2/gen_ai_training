package com.epam.training.gen.ai.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Data
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    String key;
    String endpoint;
    Map<String, String> models;
}