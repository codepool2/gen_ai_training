package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.client.RagService;
import com.epam.training.gen.ai.model.QueryInput;
import com.epam.training.gen.ai.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class QueryController {



    @Autowired
    RagService ragService;

    @Autowired
    KnowledgeRepository knowledgeRepository;



    @PostMapping("/rag/response")
    public String getResponseFromRag(@RequestBody QueryInput input) {
        return ragService.getQueryResponse(input.getInput());
    }

    @PostMapping("/addData")
    public String addData(@RequestBody QueryInput input){
         knowledgeRepository.addData(input.getInput());
        System.out.println("Added external data successfully");
        return "Success";
    }

}
