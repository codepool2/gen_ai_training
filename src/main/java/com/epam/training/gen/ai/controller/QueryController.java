package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.model.*;
import com.epam.training.gen.ai.prompt.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class QueryController {

    @Autowired
    PromptService promptService;

    @PostMapping("/openAi")
    public List<QueryResponse> getResponse(@RequestBody QueryInput input){
        return promptService.getResponse(input.getInput(), AiModel.OPEN_AI);
    }

    @PostMapping("/amazon")
    public List<QueryResponse> getResponseFromAmazon(@RequestBody QueryInput input){
        return promptService.getResponse(input.getInput(), AiModel.AMAZON);
    }

    @PostMapping("/jiraPlugin")
    public String getJiraDashBoards(@RequestBody QueryInput input){
        return promptService.getJiraDashboard(input.getInput(), AiModel.OPEN_AI);
    }

}
