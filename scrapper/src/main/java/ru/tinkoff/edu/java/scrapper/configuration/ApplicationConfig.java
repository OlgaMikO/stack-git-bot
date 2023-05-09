package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.database.AccessType;

@Validated
@ConfigurationProperties(prefix = "scrapper.app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull String scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull Integer countOldLinks,
                                @NotNull Boolean useQueue,
                                @NotNull String exchangeName,
                                @NotNull String queueName,
                                @NotNull String routingKey) {
    @Bean
    public String getInterval(){
        return String.valueOf(scheduler);
    }

}
