package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.QueryResponse;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemanticKernelService {

  private final ChatCompletionService chatCompletionService;
  private final InvocationContext invocationContext;
  private final Kernel kernel;
  private final Gson gson;
  private final ChatHistory chatHistory;

  public SemanticKernelService(
          ChatCompletionService chatCompletionService,
          InvocationContext invocationContext,
          ChatHistory chatHistory,
          Kernel kernel) {
    this.chatCompletionService = chatCompletionService;
    this.invocationContext = invocationContext;
    this.chatHistory = new ChatHistory();
    this.kernel = kernel;
    this.gson = new Gson();
  }


  public List<QueryResponse> getResponse(String query) {

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
                 - please make sure the response is properly closed with json braces, so that I can serialize the text into json object
                 - Please limit the number of words to 40
                Here is the question you should answer: %s
                """,
            query);
    chatHistory.addUserMessage(promptWithFormat);
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


  public String getAllJirasWithSpecificStatus(String input){
   chatHistory.addUserMessage(input);
    List<ChatMessageContent<?>> queryResponse =
            chatCompletionService
                    .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                    .block();
    return String.valueOf(queryResponse.get(0));
  }


}
