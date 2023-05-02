package ru.tinkoff.edu.java.scrapper.configuration.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client")
public record ClientConfigProperties(@NotNull String gitHubBaseUrl,
                                     @NotNull String stackOverflowBaseUrl,
                                     @NotNull String botClientBaseUrl) {
}
