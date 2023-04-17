package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.Chat;

import java.util.List;

public abstract class ChatDao {

    public abstract int add(Chat chat);

    public abstract int remove(Long id);

    public abstract List<Chat> findAll();

}
