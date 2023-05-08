package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class Mapper {

    private static Mapper instance;

    private Mapper(){}

    public static Mapper getInstance(){
        if(instance == null){
            instance = new Mapper();
        }
        return instance;
    }

    private RowMapper<Chat> chatRowMapper() {
        return (resultSet, rowNum) -> new Chat(resultSet.getLong("Id"));
    }

    public RowMapper<Chat> getChatRowMapper() {
        return chatRowMapper();
    }

    private RowMapper<Link> linkRowMapper() {
        return (resultSet, rowNum) -> {
            Link link = new Link();
            link.setId(resultSet.getLong("Id"));
            link.setUrl(URI.create(resultSet.getString("URL")));
            link.setChat(resultSet.getLong("Chat"));
            link.setLastActivity(resultSet.getTimestamp("Last_activity").toLocalDateTime().atOffset(ZoneOffset.of("+03:00")));
            link.setLastUpdate(resultSet.getTimestamp("Last_update").toLocalDateTime().atOffset(ZoneOffset.of("+03:00")));
            return link;
        };
    }

    public RowMapper<Link> getLinkRowMapper() {
        return linkRowMapper();
    }
}
