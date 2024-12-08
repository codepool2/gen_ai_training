package com.epam.training.gen.ai.client;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import java.util.List;

public class BabyBuddyService {

    private final ChatCompletionService chatCompletionService;
    private final InvocationContext invocationContext;
    private final ChatHistory chatHistory;
    private final Kernel kernel;

    public BabyBuddyService(ChatCompletionService chatCompletionService, InvocationContext invocationContext, Kernel kernel) {
        this.chatCompletionService = chatCompletionService;
        this.invocationContext = invocationContext;
        this.kernel = kernel;
        this.chatHistory = new ChatHistory();
        initChatHistoryWithSystemPrompts();
    }

    private void initChatHistoryWithSystemPrompts() {
        this.chatHistory.addSystemMessage("Your role is to interact with children of age 5 to 10 years, " +
                "you have to ask them riddles, they will try to answer, If they answer correctly, you should appreciate them and should ask next riddle." +
                "If they give incorrect answer, you should given them hints to approach at the correct answer, When the child answers that he is not able to answer, give them correct answer"
                + "After playing for few minutes, suggest them nicely to do some physical activities as too much time on internet disturbs their creativity");
    }

    public String generateRiddle(String input) {
        String prompt = String.format("Based on child's input: \"%s\", give response", input);
        chatHistory.addUserMessage(prompt);
        List<ChatMessageContent<?>> queryResponse =
                chatCompletionService
                        .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                        .block();
        StringBuilder response = new StringBuilder();
        for (ChatMessageContent<?> result : queryResponse) {
            chatHistory.addMessage(result);

            if(result.getAuthorRole() == AuthorRole.ASSISTANT){
                System.out.println("Riddle Solving Assistant Result: "+result);
            }
            response.append(result);
        }

        return response.toString();
    }
}
