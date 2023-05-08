import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Testcontainers
public abstract class IntegrationEnvironment {

    private static final String IMAGE_VERSION = "postgres:15";
    private static final String DB_NAME = "scrapper";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwerty";

    static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER;
    private static final Connection connection;
    private static final Database database;


    @TestConfiguration
    static class Config {
        @Bean
        public DataSource dataSource() {
            String url = POSTGRES_SQL_CONTAINER.getJdbcUrl();
            String user = POSTGRES_SQL_CONTAINER.getUsername();
            String password = POSTGRES_SQL_CONTAINER.getPassword();
            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setUrl(url);
            driverManagerDataSource.setUsername(user);
            driverManagerDataSource.setPassword(password);
            driverManagerDataSource.setDriverClassName(POSTGRES_SQL_CONTAINER.getDriverClassName());
            return driverManagerDataSource;
        }

//        @Bean
//        public PlatformTransactionManager transactionManager() {
//            JdbcTransactionManager transactionManager = new JdbcTransactionManager();
//            transactionManager.setDataSource(transactionManager.getDataSource());
//            return transactionManager;
//        }

//        @Bean
//        public JdbcTemplate jdbcTemplate() {
//            return new JdbcTemplate(dataSource());
//        }
    }

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION);
        POSTGRES_SQL_CONTAINER.withDatabaseName(DB_NAME)
                .withUsername(DB_USER)
                .withPassword(DB_PASSWORD);
        POSTGRES_SQL_CONTAINER.start();

        String url = POSTGRES_SQL_CONTAINER.getJdbcUrl();
        String user = POSTGRES_SQL_CONTAINER.getUsername();
        String password = POSTGRES_SQL_CONTAINER.getPassword();

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Database getDatabase() {
        return database;
    }

}
