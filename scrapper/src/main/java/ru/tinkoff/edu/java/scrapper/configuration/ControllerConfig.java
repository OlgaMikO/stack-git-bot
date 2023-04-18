package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.tinkoff.edu.java.scrapper.domain.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

import javax.sql.DataSource;

@Configuration
public class ControllerConfig {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("qwerty");
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public LinkDaoImpl getLinkRepository() {
        return new LinkDaoImpl(getJdbcTemplate());
    }

    @Bean
    public ChatDaoImpl getChatRepository() {
        return new ChatDaoImpl(getJdbcTemplate());
    }

    @Bean
    public JdbcLinkService getLinkService() {
        return new JdbcLinkService(getChatRepository(), getLinkRepository());
    }

    @Bean
    public JdbcTgChatService getTgChatService() {
        return new JdbcTgChatService(getChatRepository());
    }
}
