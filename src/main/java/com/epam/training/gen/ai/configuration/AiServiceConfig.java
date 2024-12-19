package com.epam.training.gen.ai.configuration;

import com.epam.training.gen.ai.client.AiClientServiceProvider;
import com.epam.training.gen.ai.client.SemanticKernelService;
import com.epam.training.gen.ai.prompt.PromptService;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceConfig {

    @Bean
    public PromptService promptService(SemanticKernelService semanticKernelService) {
        return new PromptService(semanticKernelService);
    }

    @Bean
    public SemanticKernelService semanticKernelService(AiClientServiceProvider aiClientServiceProvider

    ) {
        return new SemanticKernelService(aiClientServiceProvider);
    }

}
