package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JdbcChatDAOTest extends IntegrationTest {
    @Autowired
    private ChatRepository chatRepository;

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
    void testIsChatExist() {
        chatRepository.add(777L);

        assertTrue(chatRepository.isChatExist(777L));

        assertFalse(chatRepository.isChatExist(123L));

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
}
