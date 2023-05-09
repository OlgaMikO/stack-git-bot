import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.LinkDao;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {ScrapperApplication.class, IntegrationEnvironment.Config.class})
public class JpaTest extends DatabaseTest {

    @Autowired
    private ChatDao jpaChatDao;

    @Autowired
    private LinkDao jpaLinkDao;

    @DynamicPropertySource
    static void jpaProperties(DynamicPropertyRegistry registry) {
        registry.add("scrapper.app.databaseAccessType", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat chat = new Chat(1L);
        jpaChatDao.add(chat);
        List<Chat> chatList = jpaChatDao.findAll();
        assertEquals(chat, chatList.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Chat chat = new Chat(1L);
        jpaChatDao.add(chat);
        jpaChatDao.remove(chat.getId());
        List<Chat> chatList = jpaChatDao.findAll();
        assertEquals(new ArrayList<Chat>(), chatList);
    }
}
