package edu.java.scrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContainerTest {

    public static PostgreSQLContainer<?> POSTGRES = IntegrationTest.POSTGRES;

    @Test
    public void testContainerIsRunning() {
        assertThat(POSTGRES.isRunning()).isTrue();
    }

    @Test
    public void testContainer_INSERT_and_SELECT() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
            .create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());

        jdbcTemplate.update("INSERT INTO chat (id, chat_id) VALUES (?, ?)", 1, 409524113L);
        long chatId = jdbcTemplate.queryForObject("SELECT chat_id FROM chat WHERE id = (?)", Integer.class, 1);

        Assertions.assertThat(409524113L).isEqualTo(chatId);
    }

}
