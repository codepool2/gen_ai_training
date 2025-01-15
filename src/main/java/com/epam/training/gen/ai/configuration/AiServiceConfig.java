package com.epam.training.gen.ai.configuration;

import com.epam.training.gen.ai.client.RagService;
import com.epam.training.gen.ai.rag.RagClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceConfig {


    @Bean
    public RagService ragService(RagClient ragClient) {
        return new RagService(ragClient);
    }

}
