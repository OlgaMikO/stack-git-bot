package ru.tinkoff.edu.java.scrapper.configuration.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.service.database.LinkService;
import ru.tinkoff.edu.java.scrapper.service.database.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.database.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.database.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper.app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {

    private final ApplicationConfig config;

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatDao getChatDao() {
        return new ChatDaoImpl(jdbcTemplate);
    }

    @Bean
    public LinkDao getLinkDao() {
        return new LinkDaoImpl(jdbcTemplate, getCountOldLinks());
    }

    @Bean
    public LinkService getJdbcLinkService() {
        return new JdbcLinkService(getChatDao(), getLinkDao());
    }

    @Bean
    public TgChatService getTgChatService() {
        return new JdbcTgChatService(getChatDao());
    }

    @Bean
    public Integer getCountOldLinks() {
        return config.countOldLinks();
    }

}
