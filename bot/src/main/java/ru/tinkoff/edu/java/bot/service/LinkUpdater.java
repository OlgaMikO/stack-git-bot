package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.Bot;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class LinkUpdater {

    private final Bot bot;

    public void sendMessage(LinkUpdateRequest request){
        bot.execute(new SendMessage(request.getId(), String.format("Обновление ссылки: %s", request.getUrl())));
    }
}
