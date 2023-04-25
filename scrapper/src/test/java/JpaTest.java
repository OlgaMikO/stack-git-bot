import jakarta.transaction.Transactional;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = ScrapperApplication.class)
public class JpaTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatDao jpaChatDao;


    @Autowired
    private JpaLinkDao jpaLinkDao;

    @BeforeAll
    public static void setup() throws LiquibaseException, FileNotFoundException {
        Path path = new File(".").toPath().toAbsolutePath()
                .getParent()
                .getParent();
        ResourceAccessor accessor = new DirectoryResourceAccessor(path);
        Liquibase liquibase = new liquibase.Liquibase("migrations/master.xml", accessor, IntegrationEnvironment.getDatabase());
        liquibase.update(new Contexts(), new LabelExpression());

    }

    @AfterAll
    public static void done() throws SQLException {
        IntegrationEnvironment.getConnection().close();
    }

    @Test
    @Transactional
    @Rollback
    void addTest(){
        Chat chat = new Chat(1L);
        jpaChatDao.add(chat);
        Link link = new Link(URI.create("https://github.com/OlgaMikO/stack-git-bot"), chat.getId());
        link.setId(jpaLinkDao.add(link));
        List<Link> linkList = jpaLinkDao.findAll();
        List<Chat> chatList = jpaChatDao.findAll();
        System.out.println(chatList);
        assertEquals(link, linkList.get(0));
        assertEquals(chat, chatList.get(0));
    }
}
