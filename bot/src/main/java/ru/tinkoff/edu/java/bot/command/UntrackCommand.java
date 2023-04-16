package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.AllLinkParser;
import ru.tinkoff.edu.java.bot.DataBaseHolder;

import java.net.URI;

public class UntrackCommand implements Command {

    private final AllLinkParser parser = new AllLinkParser();
    private String responseMessage;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        if (supports(update)) {
            if (DataBaseHolder.get().containUser(update.message().chat().id())) {
                DataBaseHolder.get().deleteLink((update.message().chat().id()), update.message().text().split(" ")[1]);
                responseMessage = "Ссылка не отслеживается";
            } else {
                responseMessage = "Пользователь не зарегистрирован";
            }
        } else {
            responseMessage = "Неправильный формат ссылки";
        }
        return new SendMessage(update.message().chat().id(), responseMessage);
    }

    @Override
    public boolean supports(Update update) {
        String[] cmd = update.message().text().split(" ");
        if (cmd.length == 1) {
            return false;
        }
        return parser.getLinkParser().parseLink(URI.create(cmd[1])) != null;
    }
}
