import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class IntegrationEnvironment {
    @Container
    public static PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("qwerty")
            .withExposedPorts(5432);

    @TestConfiguration
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(POSTGRES_SQL_CONTAINER.getJdbcUrl())
                    .username(POSTGRES_SQL_CONTAINER.getUsername())
                    .password(POSTGRES_SQL_CONTAINER.getPassword())
                    .build();
        }
    }

}
