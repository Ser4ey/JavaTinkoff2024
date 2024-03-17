package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class JdbcLinkDAOTest extends IntegrationTest {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    void testAdd_FindAll() {
        chatRepository.add(420L);
        Integer chatId = chatRepository.findByUniqueChatId(420L).get().id();

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));

        var links = linkRepository.findAll();

        assertEquals(links.size(), 2);
    }

    @Test
    @Transactional
    @Rollback
    void testAdd_FindAll2() {
        chatRepository.add(420L);
        chatRepository.add(422L);
        Integer chatId1 = chatRepository.findByUniqueChatId(420L).get().id();
        Integer chatId2 = chatRepository.findByUniqueChatId(422L).get().id();

        linkRepository.add(chatId1, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.add(chatId2, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));

        var links1 = linkRepository.findAll(chatId1);
        var links2 = linkRepository.findAll(chatId2);

        assertEquals(links1.size(), 1);
        assertEquals(links1.size(), 1);

        assertEquals(links1.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4");
        assertEquals(links2.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5");

    }

    @Test
    @Transactional
    @Rollback
    void testFindByUrl() {
        chatRepository.add(420L);
        Integer chatId = chatRepository.findByUniqueChatId(420L).get().id();

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        var link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        assertFalse(link.isEmpty());
        assertEquals(link.get().url(), URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
    }


    @Test
    @Transactional
    @Rollback
    void testRemove() {
        chatRepository.add(420L);
        Integer chatId = chatRepository.findByUniqueChatId(420L).get().id();

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        var link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        assertFalse(link.isEmpty());

        linkRepository.remove(link.get().id());

        link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        assertTrue(link.isEmpty());
    }
}

