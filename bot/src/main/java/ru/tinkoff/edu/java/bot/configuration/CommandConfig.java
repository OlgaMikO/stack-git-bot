package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.DataBaseMock;
import ru.tinkoff.edu.java.bot.command.Command;
import ru.tinkoff.edu.java.bot.command.HelpCommand;
import ru.tinkoff.edu.java.bot.command.ListCommand;
import ru.tinkoff.edu.java.bot.command.StartCommand;
import ru.tinkoff.edu.java.bot.command.TrackCommand;
import ru.tinkoff.edu.java.bot.command.UntrackCommand;

@Configuration
public class CommandConfig {

    @Bean
    public Command getHelpCommand() {
        return new HelpCommand();
    }

    @Bean
    public Command getListCommand() {
        return new ListCommand(DataBaseMock.getInstance());
    }

    @Bean
    public Command getStartCommand() {
        return new StartCommand();
    }

    @Bean
    public Command getTrackCommand() {
        return new TrackCommand();
    }

    @Bean
    public Command getUntrackCommand() {
        return new UntrackCommand();
    }
}
