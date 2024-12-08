package com.epam.training.gen.ai.configuration;

import com.epam.training.gen.ai.client.BabyBuddyService;
import com.epam.training.gen.ai.client.SemanticKernelService;
import com.epam.training.gen.ai.prompt.PromptService;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;

@Configuration
public class GenAiServiceConfig {

    @Bean
    public PromptService promptService(SemanticKernelService semanticKernelService, BabyBuddyService babyBuddyService) {
        return new PromptService(semanticKernelService, babyBuddyService);
    }

    @Bean
    public BabyBuddyService babyBuddyService(ChatCompletionService chatCompletionService,
                                             InvocationContext invocationContext, Kernel kernel) {

        return new BabyBuddyService(chatCompletionService, invocationContext, kernel);
    }

    @Bean
    public SemanticKernelService semanticKernelService(ChatCompletionService chatCompletionService,
                                                       InvocationContext invocationContext,
                                                       ChatHistory chatHistory, Kernel kernel

    ) {
        return new SemanticKernelService(chatCompletionService, invocationContext, chatHistory, kernel);
    }

}
