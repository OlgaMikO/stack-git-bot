package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.DataBaseMock;

import java.util.ArrayList;

public class ListCommand implements Command{


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
        if (supports(update)){
            ArrayList<String> list = DataBaseMock.getInstance().getLinkList(update.message().chat().id());
            if(list.isEmpty()){
                return new SendMessage(update.message().chat().id(), "Список отслеживаемых ссылок пуст");
            }
            return new SendMessage(update.message().chat().id(), list.toString());
        }
        return new SendMessage(update.message().chat().id(), "Пользователь не зарегистрирован");
    }

    @Override
    public boolean supports(Update update) {
        return DataBaseMock.getInstance().containUser(update.message().chat().id());
    }
}
