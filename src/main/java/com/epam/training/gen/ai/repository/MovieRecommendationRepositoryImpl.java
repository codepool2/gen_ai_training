package com.epam.training.gen.ai.repository;

import com.epam.training.gen.ai.model.Movie;
import com.epam.training.gen.ai.model.MovieDto;
import com.epam.training.gen.ai.spi.MovieRecommendationRepository;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStore;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreRecordCollectionOptions;
import com.microsoft.semantickernel.data.vectorsearch.VectorSearchResult;
import com.microsoft.semantickernel.data.vectorsearch.VectorSearchResults;
import com.microsoft.semantickernel.data.vectorstorage.VectorStoreRecordCollection;

import java.util.List;
import java.util.stream.Collectors;

public class MovieRecommendationRepositoryImpl implements MovieRecommendationRepository {


    private final VectorStoreRecordCollection<String, MovieDto> recordCollection;
    private final OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService;

    public MovieRecommendationRepositoryImpl(JDBCVectorStore jdbcVectorStore,
                                             OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService) {
        this.recordCollection = jdbcVectorStore.getCollection("movie_recommendations",
                JDBCVectorStoreRecordCollectionOptions.<MovieDto>builder()
                        .withRecordClass(MovieDto.class)
                        .build()).createCollectionIfNotExistsAsync().block();
        this.openAITextEmbeddingGenerationService = openAITextEmbeddingGenerationService;
    }


    @Override
    public void addMovies(List<Movie> movies) {

        movies
                .forEach(movie -> {
                    List<Float> embeddingVector =
                            openAITextEmbeddingGenerationService
                                    .generateEmbeddingAsync(movie.getDescription()).block().getVector();
                    MovieDto movieDto = new MovieDto(movie.getId(), movie.getDescription(), embeddingVector);
                    String key = recordCollection.upsertAsync(movieDto, null).block();
                    System.out.println("Successfully Inserted document with key" + key);

                });
    }

    @Override
    public List<Movie> getRecommendations(String userPreference) {

        List<Float> userQuery = openAITextEmbeddingGenerationService.generateEmbeddingAsync(userPreference)
                .block()
                .getVector();

        VectorSearchResults<MovieDto> results = recordCollection.searchAsync(userQuery, null).block();
        return results.getResults()
                .stream()
                .peek(this::logRecord)
                .map(VectorSearchResult::getRecord)
                .map(re -> new Movie(re.getId(), re.getMovieDescription()))
                .collect(Collectors.toList());
    }


    private void logRecord(VectorSearchResult<MovieDto> vectorSearchResult) {

        System.out.printf("Search result with score: %f.%n id: %s, description: %s%n",
                vectorSearchResult.getScore(), vectorSearchResult.getRecord().getId(),
                vectorSearchResult.getRecord().getMovieDescription());
    }


}
