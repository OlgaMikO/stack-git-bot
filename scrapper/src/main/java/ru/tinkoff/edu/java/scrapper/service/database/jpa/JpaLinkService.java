package ru.tinkoff.edu.java.scrapper.service.database.jpa;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.database.LinkService;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final ChatDao chatDao;

    private final LinkDao linkDao;

    private static final String USER_NOT_FOUND = "Пользователь не найден";

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link(url, tgChatId);
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException(USER_NOT_FOUND, tgChatId);
        } else {
            link.setId(linkDao.add(link));
        }
        if (link.getId() == null) {
            return null;
        }
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = new Link(url, tgChatId);
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException(USER_NOT_FOUND, tgChatId);
        } else {
            Long id = linkDao.findByUrlAndChatId(url, tgChatId).getId();
            if (id == null) {
                throw new NotFoundScrapperException("Ссылка не найдена", tgChatId);
            } else {
                link.setId(id);
                linkDao.remove(id);
            }
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        List<Link> links;
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException(USER_NOT_FOUND, tgChatId);
        } else {
            links = linkDao.findAll();
        }
        return links;
    }
}
