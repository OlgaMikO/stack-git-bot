package ru.tinkoff.edu.java.bot.client;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.ScrapperResponse;

@Slf4j
public class ScrapperClient implements IScrapperClient {

    private static final String TG_CHAT_URL = "/tg-chat/{id}";
    private static final String LINKS_URL = "/links";
    private static final String TG_HEADER = "Tg-Chat-Id";

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
            .uri(TG_CHAT_URL, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public ScrapperResponse deleteChat(Long id) {
        return webClientBuilder
            .build()
            .delete()
            .uri(TG_CHAT_URL, id)
            .retrieve()
            .bodyToMono(ScrapperResponse.class)
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        webClientBuilder.defaultHeader(TG_HEADER, String.valueOf(id));
        return webClientBuilder
            .build()
            .get()
            .uri(LINKS_URL, id)
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLinkTracking(Long id, URI link) {
        webClientBuilder.defaultHeader(TG_HEADER, String.valueOf(id));
        return webClientBuilder
            .build()
            .post()
            .uri(LINKS_URL, id)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLinkTracking(Long id, URI link) {
        webClientBuilder.defaultHeader(TG_HEADER, String.valueOf(id));
        return webClientBuilder
            .build()
            .delete()
            .uri(LINKS_URL, id)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
