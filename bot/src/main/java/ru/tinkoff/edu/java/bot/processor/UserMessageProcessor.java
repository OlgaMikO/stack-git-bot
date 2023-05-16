package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import ru.tinkoff.edu.java.bot.command.Command;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
