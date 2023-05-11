package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

@SuppressWarnings({"FinalClass"})
public class Mapper {

    private static Mapper instance;

    private Mapper() {
    }

    public static Mapper getInstance() {
        if (instance == null) {
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
            link.setLastActivity(OffsetDateTime.of(
                resultSet.getTimestamp("Last_activity").toLocalDateTime(),
                ZoneOffset.UTC
            ));
            link.setLastUpdate(OffsetDateTime.of(
                resultSet.getTimestamp("Last_update").toLocalDateTime(),
                ZoneOffset.UTC
            ));
            return link;
        };
    }

    public RowMapper<Link> getLinkRowMapper() {
        return linkRowMapper();
    }
}
