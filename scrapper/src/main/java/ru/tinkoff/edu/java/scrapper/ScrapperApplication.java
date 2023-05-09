package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubApiClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowApiClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, ClientConfigProperties.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);

        GitHubApiClient gitHubApiClient = ctx.getBean(ClientConfiguration.class).gitHubApiClient();
        System.out.println(gitHubApiClient.fetchRepository("OlgaMikO", "stack-git-bot"));

        StackOverflowApiClient stackOverflowApiClient = ctx.getBean(ClientConfiguration.class).stackOverflowApiClient();
        System.out.println(stackOverflowApiClient.fetchQuestion(75868411L));
    }
}