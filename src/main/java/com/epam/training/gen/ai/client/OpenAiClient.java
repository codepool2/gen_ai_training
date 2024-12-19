package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.JiraStory;
import com.epam.training.gen.ai.model.QueryResponse;
import com.epam.training.gen.ai.prompt.TemplateSpecification;
import com.epam.training.gen.ai.spi.AiClient;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OpenAiClient implements AiClient {

    private final ChatCompletionService chatCompletionService;
    private final ChatHistory chatHistory;
    private final Kernel kernel;
    private final Gson gson;
    private final InvocationContext invocationContext;


    public OpenAiClient(ChatCompletionService chatCompletionService, Kernel kernel, Gson gson, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.chatHistory = new ChatHistory();
        this.kernel = kernel;
        this.gson = gson;
        this.invocationContext = invocationContext;
        loadSystemPrompts();
    }

    private void loadSystemPrompts() {
        Handlebars handlebars = new Handlebars();
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("prompt-template.yaml").getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Yaml yaml = new Yaml(new Constructor(TemplateSpecification.class, new LoaderOptions()));
        TemplateSpecification templateSpecification = yaml.loadAs(inputStream, TemplateSpecification.class);
        Template template;
        try {
            template = handlebars.compileInline(templateSpecification.getTemplate());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.chatHistory.addSystemMessage(template.text());
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

   /* public Map<String, List<JiraStory>> getJiras(String inputQuery) {
        chatHistory.addUserMessage(inputQuery);
        List<ChatMessageContent<?>> queryResponse =
                chatCompletionService
                        .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                        .block();

        Type empMapType = new TypeToken<Map<String, List<JiraStory>>>() {}.getType();

        System.out.println(String.valueOf(queryResponse.getFirst()));
        Map<String, List<JiraStory>> empMapType1 = gson.fromJson(String.valueOf(queryResponse.getFirst()), empMapType);
        return empMapType1;
    }*/

    public String getJiras(String inputQuery) {
        chatHistory.addUserMessage(inputQuery);
        List<ChatMessageContent<?>> queryResponse =
                chatCompletionService
                        .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                        .block();

        StringBuilder response = new StringBuilder();

        for (ChatMessageContent<?> result : queryResponse) {
            response.append(result);
        }

        return response.toString();
    }
}
