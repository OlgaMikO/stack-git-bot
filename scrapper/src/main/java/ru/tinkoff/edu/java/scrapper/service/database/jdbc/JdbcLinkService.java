package ru.tinkoff.edu.java.scrapper.service.database.jdbc;

import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.database.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public class JdbcLinkService implements LinkService {

    private final ChatDaoImpl chatDao;

    private final LinkDaoImpl linkDao;

    public JdbcLinkService(ChatDaoImpl chatDao, LinkDaoImpl linkDao) {
        this.chatDao = chatDao;
        this.linkDao = linkDao;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link(url, tgChatId);
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException(tgChatId);
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
            throw new NotFoundScrapperException(tgChatId);
        } else {
            Long id = linkDao.findByUrlAndChatId(url, tgChatId).getId();
            if (id == null) {
                throw new NotFoundScrapperException("Ссылка не найдена");
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
            throw new NotFoundScrapperException(tgChatId);
        } else {
            links = linkDao.findAll();
        }
        return links;
    }


}
