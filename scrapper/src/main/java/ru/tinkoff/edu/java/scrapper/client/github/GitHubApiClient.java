package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public class GitHubApiClient implements GitHubClient {

    private final WebClient.Builder webClientBuilder = WebClient.builder()
            .defaultHeader("Accept", "application/vnd.github+json")
            //.defaultHeader("Authorization", "Bearer <>")
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28");

    public GitHubApiClient(String baseUrl) {
        webClientBuilder.baseUrl(baseUrl);
    }

    @Override
    public GitHubResponse fetchRepository(String user, String repository) {
       return webClientBuilder
                .build()
                .get()
                .uri("/repos/{user}/{repository}", user, repository)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .block();
    }
}
