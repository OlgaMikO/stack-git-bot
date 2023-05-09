package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.BotConfiguration;
import ru.tinkoff.edu.java.bot.configuration.ClientConfiguration;
import ru.tinkoff.edu.java.bot.processor.MessageProcessor;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, ClientConfiguration.class})
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        Bot bot = new Bot(ctx.getBean(BotConfiguration.class).getToken(), ctx.getBean(MessageProcessor.class));
    }
}