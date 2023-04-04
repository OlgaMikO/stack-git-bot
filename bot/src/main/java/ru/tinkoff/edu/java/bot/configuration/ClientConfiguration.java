package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@ConfigurationProperties(prefix = "bot.client")
public record ClientConfiguration(@NotNull String scrapperBaseUrl) {

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(scrapperBaseUrl);
    }
}