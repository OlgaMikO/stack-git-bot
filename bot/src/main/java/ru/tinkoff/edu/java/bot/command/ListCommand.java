package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.IDataBase;

import java.util.ArrayList;

public class ListCommand implements Command {

    private final IDataBase dataBase;

    private String responseMessage;

    public ListCommand(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        if (supports(update)) {
            ArrayList<String> list = dataBase.getLinkList(update.message().chat().id());
            if (list.isEmpty()) {
                responseMessage = "Список отслеживаемых ссылок пуст";
            } else {
                responseMessage = list.toString();
            }
        } else {
            responseMessage = "Пользователь не зарегистрирован";
        }
        return new SendMessage(update.message().chat().id(), responseMessage);
    }

    @Override
    public boolean supports(Update update) {
        return dataBase.containUser(update.message().chat().id());
    }
}
