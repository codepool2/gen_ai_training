package com.epam.training.gen.ai.controller;

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

    @PostMapping()
    public List<QueryResponse> getResponse(@RequestBody QueryInput input) {
        return promptService.getResponse(input.getInput());
    }

    @PostMapping("/riddles")
    public String getRiddles(@RequestBody QueryInput input) {
        return promptService.generateRiddles(input.getInput());
    }

    @PostMapping("/jiras")
    public String getJiras(@RequestBody QueryInput input){
        return promptService.getAllJiras(input.getInput());
    }



}
