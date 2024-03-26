package edu.java.scrapper.configuration;

import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.jpa.JpaChatDAO;
import edu.java.scrapper.repository.jpa.JpaChatRepository;
import edu.java.scrapper.repository.jpa.JpaLinkDAO;
import edu.java.scrapper.repository.jpa.JpaLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Bean
    public ChatRepository jpaChatDAO() {
        return new JpaChatDAO(jpaChatRepository);
    }

    @Bean
    public LinkRepository jpaLinkDAO() {
        return new JpaLinkDAO(jpaLinkRepository);
    }
}
