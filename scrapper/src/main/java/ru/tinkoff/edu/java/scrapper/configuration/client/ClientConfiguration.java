package ru.tinkoff.edu.java.scrapper.configuration.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.IBotClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowApiClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.QueueLinkUpdate;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final ClientConfigProperties properties;

    private final ApplicationConfig config;

    @Bean
    public GitHubApiClient gitHubApiClient() {
        return new GitHubApiClient(properties.gitHubBaseUrl());
    }

    @Bean
    public StackOverflowApiClient stackOverflowApiClient() {
        return new StackOverflowApiClient(properties.stackOverflowBaseUrl());
    }



}
