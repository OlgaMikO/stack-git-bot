package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponseItems;

import java.util.Objects;

public class StackOverflowApiClient implements StackOverflowClient {

    private final WebClient.Builder webClientBuilder = WebClient.builder();

    public StackOverflowApiClient(String baseUrl) {
        webClientBuilder.baseUrl(baseUrl);
    }
    @Override
    public StackOverflowResponse fetchQuestion(Long id) {
        return Objects.requireNonNull(webClientBuilder
                        .build()
                        .get()
                        .uri("/questions/{id}?site=stackoverflow", id)
                        .retrieve()
                        .bodyToMono(StackOverflowResponseItems.class)
                        .block())
                .getItems()
                .get(0);
    }
}
