import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ru.tinkoff.edu.java.scrapper.domain.ChatDaoImpl;
import ru.tinkoff.edu.java.scrapper.domain.LinkDaoImpl;

@Configuration
public class TestConfig {

    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(IntegrationEnvironment.getDataSource());
    }

    @Bean
    public LinkDaoImpl getLinkRepository(){
        return new LinkDaoImpl(getJdbcTemplate());
    }

    @Bean
    public ChatDaoImpl getChatRepository(){
        return new ChatDaoImpl(getJdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JdbcTransactionManager transactionManager = new JdbcTransactionManager();
        transactionManager.setDataSource(IntegrationEnvironment.getDataSource());
        return transactionManager;
    }

}
