package com.epam.training.gen.ai.spi;

import com.epam.training.gen.ai.model.JiraStory;
import com.epam.training.gen.ai.model.QueryResponse;

import java.util.List;
import java.util.Map;

public interface AiClient {

    List<QueryResponse> getResponse(String inputQuery);

    default String getJiras(String inputQuery) {
        throw new IllegalArgumentException("Not Yet Implemented");
    }
}
