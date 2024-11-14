package com.montonurb.screenmatch_backend.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));

        CompletionRequest request = CompletionRequest.builder()
            .model("gpt-3.5-turbo-instruct")
            .prompt("Traduza para o português o seguinte texto: " + texto)
            .maxTokens(1000)
            .temperature(0.7)
            .build();

        var response = service.createCompletion(request);

        return response.getChoices().get(0).getText();
    }
}
