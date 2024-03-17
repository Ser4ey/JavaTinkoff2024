package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
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
        chatRepository.add(1L);

        var chats = chatRepository.findAll();
        System.out.println(chats);
        assertEquals(chats.size(), 2);
    }

    @Test
    @Transactional
    @Rollback
    void testFindByUniqueChatId() {
        chatRepository.add(777L);

        Optional<Chat> chat_exist = chatRepository.findByUniqueChatId(777L);
        Optional<Chat> chat_not_exist = chatRepository.findByUniqueChatId(123L);

        assertFalse(chat_exist.isEmpty());
        assertTrue(chat_not_exist.isEmpty());

        assertEquals(chat_exist.get().uniqueChatId(), 777L);
    }

    @Test
    @Transactional
    @Rollback
    void testRemove() {
        chatRepository.add(777L);
        Optional<Chat> chat = chatRepository.findByUniqueChatId(777L);
        assertFalse(chat.isEmpty());

        chatRepository.remove(chat.get().id());

        chat = chatRepository.findByUniqueChatId(777L);
        assertTrue(chat.isEmpty());
    }
}
