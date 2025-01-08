package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.model.Movie;
import com.epam.training.gen.ai.model.QueryInput;
import com.epam.training.gen.ai.model.QueryResponse;
import com.epam.training.gen.ai.prompt.PromptService;
import com.epam.training.gen.ai.spi.MovieRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class QueryController {

    @Autowired
    PromptService promptService;


    @Autowired
    MovieRecommendationRepository movieRecommendationRepository;

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

    @PostMapping("/recommendedMovies")
    public List<Movie>  queryMovieRecommendations(@RequestBody QueryInput input){
        return movieRecommendationRepository.getRecommendations(input.getInput());
    }

    @PostMapping("/movieData")
    public String  addMovieData(@RequestBody List<Movie> input){
        movieRecommendationRepository.addMovies(input);
        return "Movies Added Successfully";
    }

}
