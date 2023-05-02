package ru.tinkoff.edu.java.scrapper.service.database;

public interface TgChatService {

    void register(long tgChatId);
    void unregister(long tgChatId);
}
