import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTest extends IntegrationEnvironment{

    private static Connection connection;
    @BeforeAll
    public static void setup() throws SQLException, LiquibaseException, FileNotFoundException {
        String url = POSTGRES_SQL_CONTAINER.getJdbcUrl();
        String user = POSTGRES_SQL_CONTAINER.getUsername();
        String password = POSTGRES_SQL_CONTAINER.getPassword();

        connection = DriverManager.getConnection(url, user, password);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Path path = new File(".").toPath().toAbsolutePath()
                .getParent()
                .getParent();
        ResourceAccessor accessor = new DirectoryResourceAccessor(path);
        Liquibase liquibase = new liquibase.Liquibase("migrations/master.xml", accessor, database);
        liquibase.update(new Contexts(), new LabelExpression());
    }
    @AfterAll
    public static void done() throws SQLException {
        connection.close();
    }

    @Test
    public void databaseTest() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet tables = metaData.getTables(null, null, "%", types);
        List<String> tablesName = new ArrayList<>();
        while (tables.next()) {
            tablesName.add(tables.getString("TABLE_NAME"));
        }
        assertTrue(tablesName.contains("chats") && tablesName.contains("links"));
    }
}
