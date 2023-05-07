package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.command.Command;
import ru.tinkoff.edu.java.bot.command.HelpCommand;
import ru.tinkoff.edu.java.bot.command.UnknownCommand;

import java.util.HashMap;
import java.util.List;

@Component
public class MessageProcessor implements UserMessageProcessor {


    private final List<Command> commands;

    private HashMap<String, Command> commandsMap;

    @Autowired(required = false)
    public MessageProcessor(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    public void initCommandsMap() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands()) {
            stringBuilder.append(command.command()).append(" - ").append(command.description()).append("\n");
        }
        commands.add(new HelpCommand(stringBuilder.toString()));
        commandsMap = new HashMap<>();
        for (Command command : commands()) {
            commandsMap.put(command.command(), command);
        }
    }

    @Override
    public SendMessage process(Update update) {
        return parseCommand(update).handle(update);
    }

    public Command parseCommand(Update update) {
        String command = update.message().text().split(" ")[0];
        if (commandsMap.containsKey(command)) {
            return commandsMap.get(command);
        }
        return new UnknownCommand();
    }
}
