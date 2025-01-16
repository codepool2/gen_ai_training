package com.epam.training.gen.ai.spi;

import com.epam.training.gen.ai.model.Movie;
import com.epam.training.gen.ai.model.MovieDto;

import java.util.List;

public interface MovieRecommendationRepository {


    void addMovies(List<Movie> movies);

    List<Movie> getRecommendations(String userPreference);

}
