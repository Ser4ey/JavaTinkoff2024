package edu.java.scrapper.configuration;

import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.jdbc.JdbcChatDAO;
import edu.java.scrapper.repository.jdbc.JdbcLinkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatRepository jdbcChatDAO() {
        return new JdbcChatDAO(jdbcTemplate);
    }

    @Bean
    public LinkRepository jdbcLinkRepository() {
        return new JdbcLinkDAO(jdbcTemplate);
    }

}

