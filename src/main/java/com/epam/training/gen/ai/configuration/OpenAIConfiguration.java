package com.epam.training.gen.ai.configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAIConfiguration {

    @Bean
    public OpenAIAsyncClient openAIAsyncClient(OpenAiClientProperties openAiClientProperties) {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(openAiClientProperties.getKey()))
                .endpoint(openAiClientProperties.getEndpoint())
                .buildAsyncClient();
    }
}
