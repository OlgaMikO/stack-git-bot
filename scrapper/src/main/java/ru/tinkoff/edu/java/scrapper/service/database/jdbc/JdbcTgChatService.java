package ru.tinkoff.edu.java.scrapper.service.database.jdbc;

import org.springframework.dao.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.database.TgChatService;

public class JdbcTgChatService implements TgChatService {

    private final ChatDaoImpl chatDao;

    public JdbcTgChatService(ChatDaoImpl chatDao) {
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
            throw new NotFoundScrapperException(tgChatId);
        } else {
            chatDao.remove(tgChatId);
        }
    }
}
