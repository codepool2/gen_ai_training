package com.epam.training.gen.ai.model;

import java.util.List;

public class JiraModelResponse {

    List<JiraModel> jiraModels;

    public JiraModelResponse(List<JiraModel> jiraModels) {
        this.jiraModels = jiraModels;
    }

    public List<JiraModel> getJiraModels() {
        return jiraModels;
    }

    public void setJiraModels(List<JiraModel> jiraModels) {
        this.jiraModels = jiraModels;
    }


    @Override
    public String toString() {
        return "JiraModelResponse{" +
                "jiraModels=" + jiraModels +
                '}';
    }
}
