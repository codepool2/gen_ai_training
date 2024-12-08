package com.epam.training.gen.ai.prompt;

import com.epam.training.gen.ai.client.BabyBuddyService;
import com.epam.training.gen.ai.client.SemanticKernelService;
import com.epam.training.gen.ai.model.QueryResponse;

import java.util.List;

public class PromptService {

    private final SemanticKernelService semanticKernelService;
    private final BabyBuddyService babyBuddyService;

    public PromptService(SemanticKernelService semanticKernelService, BabyBuddyService babyBuddyService) {
        this.semanticKernelService = semanticKernelService;
        this.babyBuddyService = babyBuddyService;
    }

    public List<QueryResponse> getResponse(String input){
        return semanticKernelService.getResponse(input);
    }

    public String generateRiddles(String input){
        return babyBuddyService.generateRiddle(input);
    }

    public String getAllJiras(String input) {
        return semanticKernelService.getAllJirasWithSpecificStatus(input);
    }
}
