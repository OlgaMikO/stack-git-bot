package ru.tinkoff.edu.java.bot.client;

import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.ScrapperResponse;

import java.net.URI;

public interface IScrapperClient {

    String registerChat(Long id);

    ScrapperResponse deleteChat(Long id);

    ListLinksResponse getLinks(Long id);

    LinkResponse addLinkTracking(Long id, URI link);

    LinkResponse deleteLinkTracking(Long id, URI link);

}
