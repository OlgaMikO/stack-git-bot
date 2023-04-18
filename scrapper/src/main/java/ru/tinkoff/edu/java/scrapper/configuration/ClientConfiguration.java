package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowApiClient;

@ConfigurationProperties(prefix = "client")
public record ClientConfiguration(@NotNull String gitHubBaseUrl,
                                  @NotNull String stackOverflowBaseUrl,
                                  @NotNull String botClientBaseUrl) {

    @Bean
    public GitHubApiClient gitHubApiClient() {
        return new GitHubApiClient(gitHubBaseUrl);
    }

    @Bean
    public StackOverflowApiClient stackOverflowApiClient() {
        return new StackOverflowApiClient(stackOverflowBaseUrl);
    }

    @Bean
    public BotClient botClient() {
        return new BotClient(botClientBaseUrl);
    }

}
