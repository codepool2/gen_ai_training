package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.model.QueryResponse;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.responseformat.JsonObjectResponseFormat;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemanticKernelService {

    private final AiClientServiceProvider aiClientServiceProvider;

    public SemanticKernelService(AiClientServiceProvider aiClientServiceProvider) {
        this.aiClientServiceProvider = aiClientServiceProvider;
    }

    public List<QueryResponse> getResponse(String query, AiModel modelName) {

        String promptWithFormat =
                String.format(
                        """
                                For any question asked,
                                 - You should written the answer.
                                 - The answer should be in below JSON format:
                                    {
                                    "input": "Question being asked,
                                    "output": Response from AI model assistant"
                                    }
                                 - The response should not contain any special characters
                                 - please make sure the response is properly closed with json braces, so that I can 
                                 serialize the text into json object
                                 - Please limit the number of words to 40
                                Here is the question you should answer: %s
                                """,
                        query);

        return aiClientServiceProvider.getAiClientProvider(modelName).getResponse(promptWithFormat);
    }
}
