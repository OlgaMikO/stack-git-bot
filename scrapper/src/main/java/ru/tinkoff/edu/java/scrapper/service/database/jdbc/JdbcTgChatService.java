package ru.tinkoff.edu.java.scrapper.service.database.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;
import ru.tinkoff.edu.java.scrapper.service.database.TgChatService;

@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private final ChatDao chatDao;

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
            throw new NotFoundScrapperException("Пользователь не найден", tgChatId);
        } else {
            chatDao.remove(tgChatId);
        }
    }
}
