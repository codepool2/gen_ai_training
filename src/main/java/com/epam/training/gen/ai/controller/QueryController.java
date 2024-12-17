package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.model.QueryInput;
import com.epam.training.gen.ai.model.QueryResponse;
import com.epam.training.gen.ai.prompt.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
