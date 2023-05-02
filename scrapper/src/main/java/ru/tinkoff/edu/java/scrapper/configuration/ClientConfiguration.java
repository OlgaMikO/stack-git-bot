package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowApiClient;

@Configuration
public class ClientConfiguration {

    private final ClientConfigProperties properties;

    @Autowired
    public ClientConfiguration(ClientConfigProperties properties){
        this.properties = properties;
    }

    @Bean
    public GitHubApiClient gitHubApiClient() {
        return new GitHubApiClient(properties.gitHubBaseUrl());
    }

    @Bean
    public StackOverflowApiClient stackOverflowApiClient() {
        return new StackOverflowApiClient(properties.stackOverflowBaseUrl());
    }

    @Bean
    public BotClient botClient() {
        return new BotClient(properties.botClientBaseUrl());
    }

}
