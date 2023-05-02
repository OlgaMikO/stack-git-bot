package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class LinkDaoImpl extends LinkDao {

    private final JdbcTemplate jdbcTemplate;

    private final Integer countOldLinks;

    @Override
    public long add(Link link) {
        String SQL = "insert into links(URL, Chat, Last_update, Last_activity) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, link.getUrl().toString());
                ps.setLong(2, link.getChat());
                ps.setTimestamp(3, Timestamp.valueOf(link.getLastUpdate().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
                ps.setTimestamp(4, Timestamp.valueOf(link.getLastActivity().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
                return ps;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return Long.parseLong(Objects.requireNonNull(keyHolder.getKeys()).get("id").toString());
    }

    @Override
    public int remove(Long id) {
        String SQL = "delete from links where id = ?";
        return jdbcTemplate.update(SQL, id);
    }

    @Override
    public List<Link> findAll() {
        String SQL = "select * from links";
        return jdbcTemplate.query(SQL, Mapper.getInstance().getLinkRowMapper());
    }

    @Override
    public Link findByUrlAndChatId(URI url, Long chatId) {
        String SQL = "select * from links where url = ? and chat = ?";
        return jdbcTemplate.query(SQL, ps -> {
                    ps.setString(1, url.toString());
                    ps.setLong(2, chatId);
                },
                Mapper.getInstance().getLinkRowMapper()).get(0);
    }

    public List<Link> orderByLastUpdate() {
        String SQL = "select * from links order by last_update limit ?";
        return jdbcTemplate.query(SQL, ps -> ps.setInt(1, countOldLinks),
                Mapper.getInstance().getLinkRowMapper());
    }

    @Override
    public List<Link> findOldLinks(Long minutes) {
        return orderByLastUpdate();
    }

    @Override
    public int update(Long id, OffsetDateTime time, Integer answerCount, Integer commentCount) {
        String SQL = "update links set last_update = ?, answer_count = ?, comment_count = ? where id = ?";
        return jdbcTemplate.update(SQL, ps -> {
            ps.setTimestamp(1, Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setLong(2, answerCount);
            ps.setLong(3, commentCount);
            ps.setLong(4, id);
        });
    }


}
