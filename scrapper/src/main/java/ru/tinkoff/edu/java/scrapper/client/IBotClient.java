package ru.tinkoff.edu.java.scrapper.client;

import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.BotResponse;

public interface IBotClient {

    BotResponse linkUpdate(LinkUpdateRequest request);
}
