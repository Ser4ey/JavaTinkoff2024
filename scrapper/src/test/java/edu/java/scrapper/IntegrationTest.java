package edu.java.scrapper;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import lombok.SneakyThrows;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        runMigrations(POSTGRES);
    }

    @SneakyThrows
    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        Path migrations = new File(".").toPath().toAbsolutePath().getParent().getParent().resolve("migrations");

        Connection connection = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());
        ResourceAccessor changelogDirectory = new DirectoryResourceAccessor(migrations);
        PostgresDatabase db = new PostgresDatabase();
        db.setConnection(new JdbcConnection(connection));

        Liquibase liquibase = new liquibase.Liquibase("master.xml", changelogDirectory, db);
        liquibase.update(new Contexts(), new LabelExpression());

        liquibase.close();
        changelogDirectory.close();
        connection.close();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
