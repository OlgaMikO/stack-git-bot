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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.LinkDaoImpl;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class JdbcLinkTest extends IntegrationEnvironment{

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
        ResourceAccessor accessor    = new DirectoryResourceAccessor(path);
        Liquibase liquibase = new liquibase.Liquibase("migrations/master.xml", accessor, IntegrationEnvironment.getDatabase());
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @AfterAll
    public static void done() throws SQLException {
        IntegrationEnvironment.getConnection().close();
    }

    @Test
    @Transactional("transactionManager")
    //@Rollback
    void addTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(0L, "https://github.com/OlgaMikO/stack-git-bot", 1L);
        linkDao.add(link);
        List<Link> linkList = jdbcTemplate.query("select * from links", linkDao.getRowMapper());
        List<Chat> chatList = jdbcTemplate.query("select * from chats", chatDao.getRowMapper());
        assertEquals(link, linkList.get(0));
        assertEquals(chat, chatList.get(0));
    }

    @Transactional
    //@Rollback
    @Test
    void removeTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(0L, "https://github.com/OlgaMikO/stack-git-bot", 1L);
        linkDao.add(link);
        chatDao.remove(1L);
        List<Link> linkList = jdbcTemplate.query("select * from links", linkDao.getRowMapper());
        List<Chat> chatList = jdbcTemplate.query("select * from chats", chatDao.getRowMapper());
        assertEquals(new ArrayList<Link>(), linkList);
        assertEquals(new ArrayList<Chat>(), chatList);
    }

    @Transactional
    //@Rollback
    @Test
    void findAllTest() {
        Chat chat = new Chat(1L);
        chatDao.add(chat);
        Link link = new Link(0L, "https://github.com/OlgaMikO/stack-git-bot", 1L);
        linkDao.add(link);
        List<Link> linkList = linkDao.findAll();
        List<Chat> chatList = chatDao.findAll();

        assertEquals(linkList, List.of(link));
        assertEquals(chatList, List.of(chat));
    }

}
