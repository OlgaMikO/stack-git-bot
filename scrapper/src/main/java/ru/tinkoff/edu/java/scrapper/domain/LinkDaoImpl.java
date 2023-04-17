package ru.tinkoff.edu.java.scrapper.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.util.List;

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
            link.setUrl(resultSet.getString("URL"));
            link.setChat(resultSet.getLong("Chat"));
            return link;
        };
    }

    public RowMapper<Link> getRowMapper(){
        return rowMapper();
    }

    @Override
    public int add(Link link) {
        String SQL = "insert into links values (?, ?, ?)";
        return jdbcTemplate.update(SQL, link.getId(), link.getUrl(), link.getChat());
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
