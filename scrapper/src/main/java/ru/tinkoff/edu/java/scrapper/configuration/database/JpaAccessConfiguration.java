package ru.tinkoff.edu.java.scrapper.configuration.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@Configuration
@ConditionalOnProperty(prefix = "scrapper.app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {

    private final ApplicationConfig config;

    @Bean
    public Integer getCountOldLinks() {
        return config.countOldLinks();
    }
}
