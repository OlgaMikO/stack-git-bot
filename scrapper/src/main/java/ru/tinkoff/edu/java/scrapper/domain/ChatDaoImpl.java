package ru.tinkoff.edu.java.scrapper.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.Chat;

import java.util.List;

@Repository
public class ChatDaoImpl extends ChatDao {

    private final JdbcTemplate jdbcTemplate;

    public ChatDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Chat> rowMapper() {
        return (resultSet, rowNum) -> new Chat(resultSet.getLong("Id"));
    }

    public RowMapper<Chat> getRowMapper() {
        return rowMapper();
    }

    @Override
    public int add(Chat chat) {
        String SQL = "insert into chats values (?)";
        return jdbcTemplate.update(SQL, pstmt -> pstmt.setLong(1, chat.getId()));
    }

    @Override
    public int remove(Long id) {
        String SQL = "delete from chats where id = ?";
        return jdbcTemplate.update(SQL, id);
    }

    @Override
    public List<Chat> findAll() {
        String SQL = "select * from chats";
        return jdbcTemplate.query(SQL, rowMapper());
    }
}
