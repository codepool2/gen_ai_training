package com.epam.training.gen.ai.configuration;

import com.epam.training.gen.ai.repository.MovieRecommendationRepositoryImpl;
import com.epam.training.gen.ai.spi.MovieRecommendationRepository;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStore;
import com.microsoft.semantickernel.data.jdbc.JDBCVectorStoreOptions;
import com.microsoft.semantickernel.data.jdbc.postgres.PostgreSQLVectorStoreQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostGresDbConfig {


    @Bean
    public PostgreSQLVectorStoreQueryProvider postgreSQLVectorStoreQueryProvider(DataSource dataSource) {
        return PostgreSQLVectorStoreQueryProvider.builder()
                .withDataSource(dataSource)
                .build();
    }

    @Bean
    public JDBCVectorStore jdbcVectorStore(DataSource dataSource,
                                           PostgreSQLVectorStoreQueryProvider postgreSQLVectorStoreQueryProvider) {
        return JDBCVectorStore.builder()
                .withDataSource(dataSource)
                .withOptions(JDBCVectorStoreOptions.builder()
                        .withQueryProvider(postgreSQLVectorStoreQueryProvider)
                        .build())
                .build();
    }

    @Bean
    public MovieRecommendationRepository movieRecommendationRepository(JDBCVectorStore jdbcVectorStore,
                                                                       OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService) {
        return new MovieRecommendationRepositoryImpl(jdbcVectorStore, openAITextEmbeddingGenerationService);
    }
}
