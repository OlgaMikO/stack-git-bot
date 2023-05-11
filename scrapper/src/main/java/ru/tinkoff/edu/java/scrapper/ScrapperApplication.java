package ru.tinkoff.edu.java.scrapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, ClientConfigProperties.class})
public class ScrapperApplication {

    private static Logger logger;

    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        logger.log(Level.INFO, config.toString());

        GitHubApiClient gitHubApiClient = ctx.getBean(ClientConfiguration.class).gitHubApiClient();
        logger.log(Level.INFO, gitHubApiClient.fetchRepository("OlgaMikO", "stack-git-bot").toString());
    }
}
