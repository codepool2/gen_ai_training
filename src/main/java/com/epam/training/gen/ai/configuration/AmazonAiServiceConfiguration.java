package com.epam.training.gen.ai.configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.client.AmazonAiClient;
import com.epam.training.gen.ai.spi.AiClient;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonAiServiceConfiguration {

    @Bean("amazonCompletionService")
    public ChatCompletionService chatCompletionService(ClientProperties clientProperties,
                                                       OpenAIAsyncClient openAIAsyncClient) {
        return OpenAIChatCompletion.builder()
                .withModelId(clientProperties.getModels().get("amazon"))
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();
    }


    @Bean("amazonAiClient")
    public AiClient amazonAiClient(@Qualifier("amazonCompletionService") ChatCompletionService amazonCompletionService,
                                   Kernel kernel,
                                   Gson gson,
                                   InvocationContext invocationContext
    ) {
        return new AmazonAiClient(amazonCompletionService, kernel, gson, invocationContext);
    }


}
