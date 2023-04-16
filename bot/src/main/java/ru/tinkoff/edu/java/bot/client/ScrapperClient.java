package ru.tinkoff.edu.java.bot.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.ScrapperResponse;

import java.net.URI;

@Slf4j
public class ScrapperClient implements IScrapperClient {

    private final WebClient.Builder webClientBuilder = WebClient.builder()
            .defaultHeader("Accept", "application/json", "application/*+json")
            .defaultHeader("content-type", "text/plain", "charset=UTF-8");

    public ScrapperClient(String baseUrl) {
        webClientBuilder.baseUrl(baseUrl);
    }

    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Response: \n");
                //append clientRequest method and url
                clientRequest
                        .bodyToMono(String.class)
                        .flatMap(body -> {
                            sb.append(body);
                            return Mono.just(clientRequest);
                        });
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }

    @Override
    public String registerChat(Long id) {
        return webClientBuilder
                .build()
                .post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public ScrapperResponse deleteChat(Long id) {
        return webClientBuilder
                .build()
                .delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(ScrapperResponse.class)
                .block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        webClientBuilder.defaultHeader("Tg-Chat-Id", String.valueOf(id));
        return webClientBuilder
                .build()
                .get()
                .uri("/links", id)
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    @Override
    public LinkResponse addLinkTracking(Long id, URI link) {
        webClientBuilder.defaultHeader("Tg-Chat-Id", String.valueOf(id));
        return webClientBuilder
                .build()
                .post()
                .uri("/links", id)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    @Override
    public LinkResponse deleteLinkTracking(Long id, URI link) {
        webClientBuilder.defaultHeader("Tg-Chat-Id", String.valueOf(id));
        return webClientBuilder
                .build()
                .delete()
                .uri("/links", id)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }
}
