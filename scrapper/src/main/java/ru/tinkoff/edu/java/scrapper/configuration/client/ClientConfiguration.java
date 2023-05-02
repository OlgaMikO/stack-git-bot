package ru.tinkoff.edu.java.scrapper.configuration.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowApiClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final ClientConfigProperties properties;

    @Bean
    public GitHubApiClient gitHubApiClient() {
        return new GitHubApiClient(properties.gitHubBaseUrl());
    }

    @Bean
    public StackOverflowApiClient stackOverflowApiClient() {
        return new StackOverflowApiClient(properties.stackOverflowBaseUrl());
    }


}
