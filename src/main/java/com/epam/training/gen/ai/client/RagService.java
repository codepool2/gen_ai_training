package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.rag.RagClient;

public class RagService {


    private final RagClient ragClient;

    public RagService(RagClient ragClient) {
        this.ragClient = ragClient;
    }

    public String getQueryResponse(String input) {
        return ragClient.getResponse(input);
    }
}
