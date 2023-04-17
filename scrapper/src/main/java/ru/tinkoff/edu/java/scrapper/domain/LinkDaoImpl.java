package ru.tinkoff.edu.java.scrapper.domain;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.net.URI;
import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class LinkDaoImpl extends LinkDao {

    private JdbcTemplate jdbcTemplate;

    public LinkDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Link> rowMapper() {
        return (resultSet, rowNum) -> {
            Link link = new Link();
            link.setId(resultSet.getLong("Id"));
            link.setUrl(URI.create(resultSet.getString("URL")));
            link.setChat(resultSet.getLong("Chat"));
            return link;
        };
    }

    public RowMapper<Link> getRowMapper(){
        return rowMapper();
    }

    @Override
    public long add(Link link) {
        String SQL = "insert into links(URL, Chat) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, link.getUrl().toString());
                ps.setLong(2, link.getChat());
                return ps;
            }, keyHolder);
        }
        catch (DuplicateKeyException e){
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
        return jdbcTemplate.query(SQL, rowMapper());
    }
}
