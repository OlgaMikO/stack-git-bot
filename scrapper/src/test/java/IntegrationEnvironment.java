import org.testcontainers.containers.PostgreSQLContainer;

public abstract class IntegrationEnvironment {


    private static final String IMAGE_VERSION = "postgres:15";
    private static final String DB_NAME = "scrapper";
    private static final String DB_USER= "postgres";
    private static final String DB_PASSWORD= "qwerty";

    static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION);
        POSTGRES_SQL_CONTAINER.withDatabaseName(DB_NAME)
                                .withUsername(DB_USER)
                                .withPassword(DB_PASSWORD);
        POSTGRES_SQL_CONTAINER.start();
    }
}
