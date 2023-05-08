import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.configuration.database.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.Mapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {ScrapperApplication.class, IntegrationEnvironment.Config.class})
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private LinkDaoImpl linkDao;

    @Autowired
    private ChatDaoImpl chatDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setup() throws LiquibaseException, FileNotFoundException {
        Path path = new File(".").toPath().toAbsolutePath()
                .getParent()
                .getParent();
        ResourceAccessor accessor = new DirectoryResourceAccessor(path);
        Liquibase liquibase = new Liquibase("migrations/master.xml", accessor, IntegrationEnvironment.getDatabase());
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @AfterAll
    public static void done() throws SQLException {
        IntegrationEnvironment.getConnection().close();
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(URI.create("https://github.com/OlgaMikO/stack-git-bot"), chat.getId());
        link.setId(linkDao.add(link));
        List<Link> linkList = jdbcTemplate.query("select * from links", Mapper.getInstance().getLinkRowMapper());
        List<Chat> chatList = jdbcTemplate.query("select * from chats", Mapper.getInstance().getChatRowMapper());
        assertEquals(link.getId(), linkList.get(0).getId());
        assertEquals(link.getUrl(), linkList.get(0).getUrl());
        assertEquals(chat, chatList.get(0));
    }

    @Transactional
    @Rollback
    @Test
    void removeTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(URI.create("https://github.com/OlgaMikO/stack-git-bot"), chat.getId());
        link.setId(linkDao.add(link));
        linkDao.remove(link.getId());
        chatDao.remove(chat.getId());
        List<Link> linkList = jdbcTemplate.query("select * from links", Mapper.getInstance().getLinkRowMapper());
        List<Chat> chatList = jdbcTemplate.query("select * from chats", Mapper.getInstance().getChatRowMapper());
        assertEquals(new ArrayList<Link>(), linkList);
        assertEquals(new ArrayList<Chat>(), chatList);
    }

    @Transactional
    @Rollback
    @Test
    void findAllTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(URI.create("https://github.com/OlgaMikO/stack-git-bot"), 1L);
        link.setId(linkDao.add(link));
        List<Link> linkList = linkDao.findAll();
        List<Chat> chatList = chatDao.findAll();
        assertEquals(linkList.get(0).getId(), link.getId());
        assertEquals(linkList.get(0).getUrl(), link.getUrl());
        assertEquals(linkList.get(0).getChat(), link.getChat());
        assertEquals(chatList, List.of(chat));
    }

}
