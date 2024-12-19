package com.epam.training.gen.ai.client;

import com.epam.training.gen.ai.model.AiModel;
import com.epam.training.gen.ai.spi.AiClient;
import org.springframework.stereotype.Component;

@Component
public class AiClientServiceProvider {


    private final AiClient openAiClient;
    private final AiClient amazonAiClient;

    public AiClientServiceProvider(AiClient openAiClient, AiClient amazonAiClient) {
        this.openAiClient = openAiClient;
        this.amazonAiClient = amazonAiClient;
    }

    public AiClient getAiClientProvider(AiModel modelName) {

        if (modelName == AiModel.OPEN_AI) {
            return openAiClient;

        } else if (modelName == AiModel.AMAZON) {
            return amazonAiClient;
        }
        throw new IllegalArgumentException();
    }

}
