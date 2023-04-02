package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UnknownCommand implements Command{

    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return "Неизвестная команда";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), description());
    }

    @Override
    public boolean supports(Update update) {
        return Command.super.supports(update);
    }
}
