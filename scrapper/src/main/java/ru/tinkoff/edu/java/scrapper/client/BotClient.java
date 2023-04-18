package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.BotResponse;

public class BotClient implements IBotClient{

    private final WebClient.Builder webClientBuilder = WebClient.builder()
            .defaultHeader("Accept", "application/json", "application/*+json")
            .defaultHeader("content-type", "text/plain", "charset=UTF-8");

    public BotClient(String baseUrl) {
        webClientBuilder.baseUrl(baseUrl);
    }


    @Override
    public BotResponse linkUpdate(LinkUpdateRequest request) {
        return webClientBuilder
                .build()
                .post()
                .uri("/updates")
                .retrieve()
                .bodyToMono(BotResponse.class)
                .block();
    }
}
