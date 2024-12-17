package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.QueryResponse;
import com.epam.training.gen.ai.spi.AiClient;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import java.util.ArrayList;
import java.util.List;

public class AmazonAiClient implements AiClient {

    private final ChatCompletionService chatCompletionService;
    private final ChatHistory chatHistory;
    private final Kernel kernel;
    private final Gson gson;
    private final InvocationContext invocationContext;

    public AmazonAiClient(ChatCompletionService chatCompletionService, Kernel kernel, Gson gson,
                          InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.chatHistory = new ChatHistory();
        this.kernel = kernel;
        this.gson = gson;
        this.invocationContext = invocationContext;
    }

    @Override
    public List<QueryResponse> getResponse(String inputQuery) {
        chatHistory.addUserMessage(inputQuery);
        List<ChatMessageContent<?>> queryResponse =
                chatCompletionService
                        .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                        .block();
        List<QueryResponse> responses = new ArrayList<>();

        for (ChatMessageContent<?> result : queryResponse) {
            chatHistory.addMessage(result);
            responses.add(gson.fromJson(String.valueOf(result), QueryResponse.class));
        }

        return responses;
    }
}
