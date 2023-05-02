package ru.tinkoff.edu.java.scrapper.configuration.database;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import ru.tinkoff.edu.java.scrapper.domain.jpa.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.LinkRepository;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "scrapper.app", name = "database-access-type", havingValue = "jpa")
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

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("qwerty");
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        return driverManagerDataSource;
    }
}
