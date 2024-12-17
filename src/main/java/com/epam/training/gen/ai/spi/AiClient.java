package com.epam.training.gen.ai.spi;

import com.epam.training.gen.ai.model.QueryResponse;

import java.util.List;

public interface AiClient {

    List<QueryResponse> getResponse(String inputQuery);
}
