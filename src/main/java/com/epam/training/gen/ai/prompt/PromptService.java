package com.epam.training.gen.ai.prompt;

import com.epam.training.gen.ai.client.SemanticKernelService;
import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.model.QueryResponse;

import java.util.List;

public class PromptService {

    private final SemanticKernelService semanticKernelService;

    public PromptService(SemanticKernelService semanticKernelService) {
        this.semanticKernelService = semanticKernelService;
    }

    public List<QueryResponse> getResponse(String input, AiModel modelName){
        return semanticKernelService.getResponse(input, modelName);
    }
}
