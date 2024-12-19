package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.model.JiraStatus;
import com.epam.training.gen.ai.model.JiraStory;
import com.epam.training.gen.ai.model.QueryResponse;

import java.util.List;
import java.util.Map;

import com.microsoft.semantickernel.contextvariables.ContextVariableType;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypeConverter;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypes;
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

    public String getJiraResponse(AiModel aiModel, String inputQuery) {

        String promptWithFormat = String.format("""
                - You should just return the response in the expected Json format given below, Please do not append any extra information to the response, because I need to deserialize the response to object
                - Please provide the information as it is returned by plugin
                - Here is your query: %s
                """, inputQuery);


        ContextVariableTypeConverter<JiraStatus> contextVariableTypeConverter = ContextVariableTypeConverter.builder(JiraStatus.class)
                .fromPromptString(this::from)
                .fromObject(st -> from(String.valueOf(st)))
                .build();

        ContextVariableTypes.addGlobalConverter(contextVariableTypeConverter);

        return aiClientServiceProvider.getAiClientProvider(aiModel).getJiras(promptWithFormat);
    }


    private JiraStatus from(String string) {

        if (string.equalsIgnoreCase("OPEN") || string.toLowerCase().contains("open")) {
            return JiraStatus.OPEN;
        } else if ((string.equalsIgnoreCase("INPROGRESS") || string.toLowerCase().contains("inprogress") || string.toLowerCase().contains("progress"))) {
            return JiraStatus.INPROGRESS;

        } else if (string.equalsIgnoreCase("DONE") || string.toLowerCase().contains("done")) {
            return JiraStatus.DONE;
        } else if (string.equalsIgnoreCase("CODEREVIEW") || string.toLowerCase().contains("codereview")) {
            return JiraStatus.CODEREVIEW;
        }

        return JiraStatus.OPEN;
    }


}
