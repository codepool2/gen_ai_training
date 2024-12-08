package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.JiraModel;
import com.epam.training.gen.ai.model.JiraModelResponse;
import com.epam.training.gen.ai.model.Status;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import java.util.*;
import java.util.stream.Collectors;

//Plugin that demonstrates Function Invocation Concepts
public class JiraLifeCyclePlugin {

    private final List<JiraModel> jiraModels = new ArrayList<>();


    public JiraLifeCyclePlugin() {

        jiraModels.addAll(Arrays.asList(new JiraModel("CookingEggs", Status.OPEN),
                new JiraModel("BakingBiscuits", Status.OPEN),
                new JiraModel("BakingCake", Status.INPROGRESS),
                new JiraModel("BoilingRice", Status.OPEN)));

    }

    @DefineKernelFunction(name = "getAllJiras",
            description = "Use this when trying to get all jiras"
    )
    public String getAllJiras() {

        System.out.println("Invoking get All Jiras function");
        return new JiraModelResponse(jiraModels.stream().toList()).toString();

    }

    @DefineKernelFunction(name = "getJiraWithsSpecificStatus",
            description = "Use this function when trying to find the Jira With Specific Status")
    public String getJiraWithSpecificStatus(@KernelFunctionParameter(name = "status", description = "Status of the Jira",
            type = String.class) String status) {

        System.out.println("Trying to find out the jira with specific status: " + status);
        return new JiraModelResponse(jiraModels.stream()
                .filter(jiraModel -> jiraModel.getStatus().name().equalsIgnoreCase(status))
                .collect(Collectors.toList())).toString();

    }

}
