package com.epam.training.gen.ai.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class JiraStory {

    String name;
    JiraStatus jiraStatus;
}
