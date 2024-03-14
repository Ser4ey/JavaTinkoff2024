package edu.java.scrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testContainer_INSERT_and_SELECT() {
        jdbcTemplate.update("INSERT INTO chat (unique_chat_id) VALUES (?)", 409524113L);
        Long chatId = jdbcTemplate.queryForObject("SELECT unique_chat_id FROM chat WHERE id = (?)", Long.class, 1);

        Assertions.assertThat(409524113L).isEqualTo(chatId);
    }

}
