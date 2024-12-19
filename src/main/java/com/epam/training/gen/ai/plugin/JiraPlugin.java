package com.epam.training.gen.ai.plugin;

import com.epam.training.gen.ai.model.JiraDashboard;
import com.epam.training.gen.ai.model.JiraStatus;
import com.epam.training.gen.ai.model.JiraStory;
import com.google.gson.Gson;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JiraPlugin {


    private final Gson gson;

    public JiraPlugin(Gson gson) {
        this.gson = gson;
    }


    @DefineKernelFunction(description = "Give the Jira Dashboard, with assignee and jiras assigned along with jira status")
    public String getJiraDashboard() {

        System.out.println("Returning all Jiras");
        return gson.toJson(JiraDashboard.jiraDashBoardWithNames);
    }


    @DefineKernelFunction(description = "Use this to return all jiras with specific status only")
    public String getJiraDashboardWithJiraOfSpecificStatus
            (@KernelFunctionParameter(description = "jira status, ex:DONE, OPEN, INPROGRESS", name = "jiraStatus", type = JiraStatus.class) JiraStatus jiraStatus) {
        System.out.println("Invoking Jira with specific status: " + jiraStatus);
        return gson.toJson(JiraDashboard.jiraDashBoardWithNames.values().stream().flatMap(Collection::stream).filter(jiraStory -> jiraStory.getJiraStatus() == jiraStatus)
                .collect(Collectors.toList()));

    }
}
