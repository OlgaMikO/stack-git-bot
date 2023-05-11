package ru.tinkoff.edu.java.scrapper.domain;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

public abstract class ChatDao {

    public abstract int add(Chat chat);

    public abstract int remove(Long id);

    public abstract List<Chat> findAll();

    public abstract Chat findById(Long id);

}
