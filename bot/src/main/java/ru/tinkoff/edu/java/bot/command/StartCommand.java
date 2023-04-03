package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.DataBaseHolder;

public class StartCommand implements Command {

    private final String responseMessage = "Пользователь зарегистрирован";

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        DataBaseHolder.get().addUser(update.message().chat().id());
        return new SendMessage(update.message().chat().id(), responseMessage);
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }

}
