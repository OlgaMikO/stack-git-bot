package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.command.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageProcessor implements UserMessageProcessor {

    private HashMap<String, Command> commandsMap;
    @Override
    public List<? extends Command> commands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        commandsMap = new HashMap<>();
        commandsMap.put("/help", new HelpCommand());
        commandsMap.put("/start", new StartCommand());
        commandsMap.put("/track", new TrackCommand());
        commandsMap.put("/untrack", new UntrackCommand());
        commandsMap.put("/list", new ListCommand());
        StringBuilder stringBuilder = new StringBuilder();
        for (Command cmd : commands) {
            stringBuilder.append(cmd.command()).append(" - ").append(cmd.description()).append("\n");
        }
        commandsMap.put("/help", new HelpCommand(stringBuilder.toString()));
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        return parseCommand(update).handle(update);
    }

    public Command parseCommand(Update update) {
        String command = update.message().text().split(" ")[0];
        if(commandsMap.containsKey(command)){
            return commandsMap.get(command);
        }
        return new UnknownCommand();
    }
}
