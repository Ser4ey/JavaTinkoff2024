package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JdbcChatDAOTest extends IntegrationTest {
    private final JdbcChatDAO chatRepository;
    private final JdbcLinkDAO linkRepository;

    JdbcChatDAOTest(@Autowired JdbcTemplate jdbcTemplate) {
        this.chatRepository = new JdbcChatDAO(jdbcTemplate);
        this.linkRepository = new JdbcLinkDAO(jdbcTemplate);
    }

    @Test
    @Transactional
    @Rollback
    void testAdd_FindAll() {
        chatRepository.add(409233092L);
        chatRepository.add(18L);

        var chats = chatRepository.findAll();
        System.out.println(chats);
        assertEquals(chats.size(), 2);
        assertEquals(chats.getFirst().chatId(), 409233092L);
        assertEquals(chats.getLast().chatId(), 18L);
    }

    @Test
    @Transactional
    @Rollback
    void testFindAllByLinkId() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var added_link1 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        var added_link2 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));
        linkRepository.addLinkRelation(chatId, added_link1.id());
        linkRepository.addLinkRelation(chatId, added_link2.id());


        var links = linkRepository.findAll();
        assertEquals(links.size(), 2);


        var links2 = linkRepository.findAllByChatId(chatId);
        assertEquals(links2.size(), 2);

        var links3 = linkRepository.findAllByChatId(-1L);
        assertEquals(links3.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void testIsChatExist() {
        chatRepository.add(777L);

        assertTrue(chatRepository.isChatExist(777L));

        chatRepository.remove(777L);

        assertFalse(chatRepository.isChatExist(123L));
        assertFalse(chatRepository.isChatExist(777L));
    }

    @Test
    @Transactional
    @Rollback
    void testRemove() {
        chatRepository.add(777L);
        assertTrue(chatRepository.isChatExist(777L));

        chatRepository.remove(777L);

        assertFalse(chatRepository.isChatExist(777L));

        assertTrue(chatRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testRemove2() {
        chatRepository.add(777L);
        chatRepository.add(111L);
        assertTrue(chatRepository.isChatExist(777L));
        assertTrue(chatRepository.isChatExist(111L));

        chatRepository.remove(777L);

        assertFalse(chatRepository.isChatExist(777L));
        assertTrue(chatRepository.isChatExist(111L));
    }

    @Test
    @Transactional
    @Rollback
    void testRemove3() {
        Long chatId1 = 777L;
        Long chatId2 = 111L;
        chatRepository.add(chatId1);
        chatRepository.add(chatId2);

        var added_link = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.addLinkRelation(chatId1, added_link.id());
        linkRepository.addLinkRelation(chatId2, added_link.id());

        assertTrue(chatRepository.isChatExist(chatId1));
        assertTrue(chatRepository.isChatExist(chatId2));

        chatRepository.remove(chatId1);

        assertFalse(chatRepository.isChatExist(chatId1));
        assertTrue(chatRepository.isChatExist(chatId2));
    }
}
