package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jpa.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.LinkRepository;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {

    private final ChatRepository chatRepository;

    private final LinkRepository linkRepository;

    @Bean
    public JpaChatDao getJpaChatDao() {
        return new JpaChatDao(chatRepository);
    }

    @Bean
    public JpaLinkDao getJpaLinkDao() {
        return new JpaLinkDao(linkRepository, chatRepository);
    }
}
