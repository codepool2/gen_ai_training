package com.epam.training.gen.ai.model;

import lombok.Data;
import lombok.Value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.training.gen.ai.model.JiraStatus.*;

@Data
@Value
public class JiraDashboard {

   public static Map<String, List<JiraStory>> jiraDashBoardWithNames = new HashMap<>();

    static {
        jiraDashBoardWithNames.put("Amar", Arrays.asList(new JiraStory("Task1", OPEN)));
        jiraDashBoardWithNames.put("Gopi", Arrays.asList(new JiraStory("Task2", DONE)));
        jiraDashBoardWithNames.put("Divya", Arrays.asList(new JiraStory("Task3", INPROGRESS)));
        jiraDashBoardWithNames.put("Shubham", Arrays.asList(new JiraStory("Task4", CODEREVIEW)));

    }
}
