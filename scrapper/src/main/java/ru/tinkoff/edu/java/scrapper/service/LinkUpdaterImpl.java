package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.AllLinkParser;
import ru.tinkoff.edu.java.answer.GitHubAnswer;
import ru.tinkoff.edu.java.answer.ParserAnswer;
import ru.tinkoff.edu.java.answer.StackOverflowAnswer;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LinkUpdaterImpl implements LinkUpdater {

    private final LinkDao linkDao;

    private final AllLinkParser parser = new AllLinkParser();

    private final GitHubClient gitHubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;

    @Autowired
    public LinkUpdaterImpl(@Qualifier("linkDaoImpl") LinkDao linkDao,
                           GitHubClient gitHubClient,
                           StackOverflowClient stackOverflowClient,
                           BotClient botClient) {
        this.linkDao = linkDao;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
    }

    @Override
    public String update() {
        List<Link> oldLinks = linkDao.findOldLinks();
        ParserAnswer answer;
        StringBuilder resultChanges = new StringBuilder();
        String change = "";
        for (Link link : oldLinks) {
            answer = parser.getLinkParser().parseLink(link.getUrl());
            switch (answer.getClassName()) {
                case "StackOverflowAnswer" -> change = stackOverflowUpdate(link, answer);
                case "GitHubAnswer" -> change = gitHubUpdate(link, answer);
            }
            if(change != null){
                resultChanges.append(change).append("\n");
                botClient.linkUpdate(new LinkUpdateRequest(link, "Обновление данных"));
            }
            linkDao.update(link.getId(), OffsetDateTime.now());
        }
        return resultChanges.toString();
    }

    public String stackOverflowUpdate(Link link, ParserAnswer answer) {
        StackOverflowResponse response = stackOverflowClient.fetchQuestion(((StackOverflowAnswer) answer).getQuestionID());
        if (response.getActivity().isAfter(link.getLastActivity())) {
            return "Данные изменились";
        }
        return null;
    }

    public String gitHubUpdate(Link link, ParserAnswer answer) {
        GitHubResponse response = gitHubClient.fetchRepository(((GitHubAnswer) answer).getUser(),
                ((GitHubAnswer) answer).getRepository());
        if (response.getUpdatedAt().isAfter(link.getLastActivity())) {
            return "Данные изменились";
        }
        return null;
    }
}
