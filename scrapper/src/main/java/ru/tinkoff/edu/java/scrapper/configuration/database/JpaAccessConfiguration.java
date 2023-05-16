package ru.tinkoff.edu.java.scrapper.configuration.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.database.LinkService;
import ru.tinkoff.edu.java.scrapper.service.database.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.database.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.database.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper.app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {

    private final ApplicationConfig config;

    private final ChatRepository chatRepository;

    private final LinkRepository linkRepository;

    @Bean
    public ChatDao jpaChatDao() {
        return new JpaChatDao(chatRepository);
    }

    @Bean
    public LinkDao jpaLinkDao() {
        return new JpaLinkDao(linkRepository, chatRepository);
    }

    @Bean
    public TgChatService jpaTgChatService() {
        return new JpaTgChatService(jpaChatDao());
    }

    @Bean
    public LinkService jpaLinkService() {
        return new JpaLinkService(jpaChatDao(), jpaLinkDao());
    }

    @Bean
    public Integer getCountOldLinks() {
        return config.countOldLinks();
    }
}
