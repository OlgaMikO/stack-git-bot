import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTest extends IntegrationEnvironment{

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
    public void databaseTest() throws SQLException {
        DatabaseMetaData metaData = IntegrationEnvironment.getConnection().getMetaData();
        String[] types = {"TABLE"};
        ResultSet tables = metaData.getTables(null, null, "%", types);
        List<String> tablesName = new ArrayList<>();
        while (tables.next()) {
            tablesName.add(tables.getString("TABLE_NAME"));
        }
        assertTrue(tablesName.contains("chats") && tablesName.contains("links"));
    }
}
