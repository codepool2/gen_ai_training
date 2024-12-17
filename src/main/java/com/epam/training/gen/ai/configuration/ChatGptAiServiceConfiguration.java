package com.epam.training.gen.ai.configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.client.AmazonAiClient;
import com.epam.training.gen.ai.client.OpenAiClient;
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
public class ChatGptAiServiceConfiguration {

    @Bean("openAiCompletionService")
    public ChatCompletionService chatGptCompletionService(ClientProperties clientProperties,
                                                          OpenAIAsyncClient openAIAsyncClient) {
        return OpenAIChatCompletion.builder()
                .withModelId(clientProperties.getModels().get("openAi"))
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();
    }

    @Bean("openAiClient")
    public AiClient openAiClient(@Qualifier("openAiCompletionService") ChatCompletionService openAiCompletionService,
                                   Kernel kernel,
                                   Gson gson,
                                   InvocationContext invocationContext
    ) {
        return new OpenAiClient(openAiCompletionService, kernel, gson, invocationContext);
    }

}
