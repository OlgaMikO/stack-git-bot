package ru.tinkoff.edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.AllLinkParser;
import ru.tinkoff.edu.java.bot.DataBaseHolder;

import java.net.URI;

public class TrackCommand implements Command {

    private final AllLinkParser parser = new AllLinkParser();

    private final String responseMessage = "Ссылка отслеживается";

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        if (supports(update)) {
            if (DataBaseHolder.get().containUser(update.message().chat().id())) {
                DataBaseHolder.get().addLink(update.message().chat().id(), update.message().text().split(" ")[1]);
                return new SendMessage(update.message().chat().id(), responseMessage);
            }
            return new SendMessage(update.message().chat().id(), "Пользователь не зарегистрирован");
        }
        return new SendMessage(update.message().chat().id(), "Неправильный формат ссылки");
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
