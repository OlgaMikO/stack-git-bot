package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.dao.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

public class JdbcTgChatService implements TgChatService {

    private final ChatDao chatDao;

    public JdbcTgChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void register(long tgChatId) {
        if (chatDao.findById(tgChatId) != null) {
            throw new DuplicateKeyException("Пользователь уже зарегистрирован");
        } else {
            chatDao.add(new Chat(tgChatId));
        }
    }

    @Override
    public void unregister(long tgChatId) {
        if (chatDao.findById(tgChatId) == null) {
            throw new NotFoundScrapperException("Пользователь не найден");
        } else {
            chatDao.remove(tgChatId);
        }
    }
}
