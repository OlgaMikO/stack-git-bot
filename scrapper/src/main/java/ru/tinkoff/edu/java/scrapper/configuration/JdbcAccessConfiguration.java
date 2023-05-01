package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "scrapper.app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {

    @Value("${scrapper.app.count-old-links}")
    int countOldLinks;

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
    public LinkDaoImpl getJdbcLinkRepository() {
        return new LinkDaoImpl(getJdbcTemplate(), getCountOldLinks());
    }

    @Bean
    public ChatDaoImpl getJdbcChatRepository() {
        return new ChatDaoImpl(getJdbcTemplate());
    }


    @Bean
    public JdbcLinkService getJdbcLinkService() {
        return new JdbcLinkService(getJdbcChatRepository(), getJdbcLinkRepository());
    }

    @Bean
    public JdbcTgChatService getJdbcTgChatService() {
        return new JdbcTgChatService(getJdbcChatRepository());
    }

    @Bean
    public int getCountOldLinks(){
        return countOldLinks;
    }

}
