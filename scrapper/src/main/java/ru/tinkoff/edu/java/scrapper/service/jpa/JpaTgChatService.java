package ru.tinkoff.edu.java.scrapper.service.jpa;

import org.springframework.dao.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

public class JpaTgChatService implements TgChatService {

    private final JpaChatDao chatDao;

    public JpaTgChatService(JpaChatDao chatDao) {
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
