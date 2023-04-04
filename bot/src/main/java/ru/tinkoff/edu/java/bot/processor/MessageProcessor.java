package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.DataBaseMock;
import ru.tinkoff.edu.java.bot.command.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageProcessor implements UserMessageProcessor {

    private HashMap<String, Command> commandsMap;

    public MessageProcessor() {
        initCommandsMap();
    }

    @Override
    public List<? extends Command> commands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand(DataBaseMock.getInstance()));
        return commands;
    }

    public void initCommandsMap() {
        commandsMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands()) {
            commandsMap.put(command.command(), command);
            stringBuilder.append(command.command()).append(" - ").append(command.description()).append("\n");
        }
        commandsMap.put("/help", new HelpCommand(stringBuilder.toString()));
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
