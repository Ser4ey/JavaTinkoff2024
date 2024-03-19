package edu.java.scrapper;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContainerTest {

    public static PostgreSQLContainer<?> POSTGRES = IntegrationTest.POSTGRES;
    public static JdbcTemplate jdbcTemplate = new JdbcTemplate(
        DataSourceBuilder.create()
        .url(POSTGRES.getJdbcUrl())
        .username(POSTGRES.getUsername())
        .password(POSTGRES.getPassword())
        .build());

    @Test
    public void testContainerIsRunning() {
        assertThat(POSTGRES.isRunning()).isTrue();
    }

    @Test
    public void testVerifyDatabaseConnection() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT 1");
        Assertions.assertThat(result).isNotEmpty();
    }

}
