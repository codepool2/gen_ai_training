package com.epam.training.gen.ai.configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.epam.training.gen.ai.rag.RagClient;
import com.epam.training.gen.ai.repository.KnowledgeRepository;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
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


    @Bean
    public RagClient ragClient(@Qualifier("openAiCompletionService") ChatCompletionService openAiCompletionService,
                               Kernel kernel,
                               Gson gson,
                               InvocationContext invocationContext, KnowledgeRepository knowledgeRepository) {

        return new RagClient(openAiCompletionService, kernel, gson, invocationContext, knowledgeRepository);
    }


    @Bean
    public OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService(OpenAIAsyncClient openAIAsyncClient) {
        return OpenAITextEmbeddingGenerationService.builder()
                .withOpenAIAsyncClient(openAIAsyncClient)
                .withModelId("text-embedding-ada-002")
                .build();

    }

    @Bean
    public InvocationContext invocationContext() {
        return InvocationContext.builder()
                .withPromptExecutionSettings(PromptExecutionSettings.builder()
                        .withTemperature(0.2)
                        .build())
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();
    }

    @Bean
    public Kernel kernel(Gson gson) {

        return Kernel.builder().
                build();
    }

    @Bean
    public OpenAIAsyncClient openAIAsyncClient(ClientProperties clientProperties) {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(clientProperties.getKey()))
                .endpoint(clientProperties.getEndpoint())
                .buildAsyncClient();
    }


}
