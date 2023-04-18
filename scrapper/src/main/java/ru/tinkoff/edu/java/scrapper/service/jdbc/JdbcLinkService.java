package ru.tinkoff.edu.java.scrapper.service.jdbc;

import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public class JdbcLinkService implements LinkService {

    private final ChatDao chatDao;

    private final LinkDao linkDao;

    public JdbcLinkService(ChatDao chatDao, LinkDao linkDao) {
        this.chatDao = chatDao;
        this.linkDao = linkDao;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link(url, tgChatId);
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException("Пользователь не найден");
        } else {
            link.setId(linkDao.add(link));
        }
        if(link.getId() == null){
            return null;
        }
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = new Link(url, tgChatId);
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException("Пользователь не найден");
        } else {
            Long id = linkDao.find(url, tgChatId).getId();
            if(id == null){
                throw new NotFoundScrapperException("Ссылка не найдена");
            }
            else {
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
            throw new NotFoundScrapperException("Пользователь не найден");
        } else {
            links = linkDao.findAll();
        }
        return links;
    }



}
