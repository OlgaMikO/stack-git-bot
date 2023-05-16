package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

@RequiredArgsConstructor
public class ChatDaoImpl extends ChatDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int add(Chat chat) {
        String SQL = "insert into chats values (?)";
        return jdbcTemplate.update(SQL, ps -> ps.setLong(1, chat.getId()));
    }

    @Override
    public int remove(Long id) {
        String SQL = "delete from chats where id = ?";
        return jdbcTemplate.update(SQL, id);
    }

    @Override
    public List<Chat> findAll() {
        String SQL = "select * from chats";
        return jdbcTemplate.query(SQL, Mapper.getInstance().getChatRowMapper());
    }

    @Override
    public Chat findById(Long id) {
        String SQL = "select * from chats where id = ?";
        List<Chat> list = jdbcTemplate.query(SQL, ps -> ps.setLong(1, id), Mapper.getInstance().getChatRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
