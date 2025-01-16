package com.epam.training.gen.ai.configuration;

import com.epam.training.gen.ai.repository.KnowledgeRepository;
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
    public KnowledgeRepository knowledgeRepository(JDBCVectorStore jdbcVectorStore,
                                                   OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService) {
        return new KnowledgeRepository(jdbcVectorStore, openAITextEmbeddingGenerationService);
    }
}
