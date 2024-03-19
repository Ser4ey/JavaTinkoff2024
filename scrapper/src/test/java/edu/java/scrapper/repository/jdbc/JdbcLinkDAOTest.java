package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
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
        Long chatId = 420L;
        chatRepository.add(chatId);

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));

        var links = linkRepository.findAll();
        assertEquals(links.size(), 2);
    }

    @Test
    @Transactional
    @Rollback
    void testAdd_FindAll2() {
        Long chatId1 = 420L;
        Long chatId2 = 793L;
        Long chatId3 = 1000000L;

        chatRepository.add(chatId1);
        chatRepository.add(chatId2);

        linkRepository.add(chatId1, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.add(chatId2, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));

        var links1 = linkRepository.findAll(chatId1);
        var links2 = linkRepository.findAll(chatId2);
        var links3 = linkRepository.findAll(chatId3);

        assertEquals(links1.size(), 1);
        assertEquals(links2.size(), 1);
        assertTrue(links3.isEmpty());

        assertEquals(links1.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4");
        assertEquals(links2.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5");

    }

    @Test
    @Transactional
    @Rollback
    void testFindByUrl() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        var link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        assertFalse(link.isEmpty());
        assertEquals(link.get().url(), URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
    }

    @Test
    @Transactional
    @Rollback
    void testRemove() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        var link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        assertFalse(link.isEmpty());

        linkRepository.remove(link.get().id());

        link = linkRepository.findByUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        assertTrue(link.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testRemoveLinkRelation() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        var links = linkRepository.findAll(chatId);
        assertFalse(links.isEmpty());
        Integer linkId = links.getFirst().id();

        linkRepository.removeLinkRelation(chatId, linkId);
        links = linkRepository.findAll(chatId);
        assertTrue(links.isEmpty());
    }

}

