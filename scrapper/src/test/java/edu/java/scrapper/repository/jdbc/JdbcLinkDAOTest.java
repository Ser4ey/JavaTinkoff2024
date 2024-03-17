package edu.java.scrapper.repository.jdbc;

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
class JdbcLinkDAOTest {
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

        linkRepository.add(chatId, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));

        System.out.println(linkRepository.findAll());
        linkRepository.findAll();
    }

    @Test
    @Transactional
    @Rollback
    void findByUniqueChatId() {
        chatRepository.add(777L);

        Optional<Chat> chat_exist = chatRepository.findByUniqueChatId(777L);
        Optional<Chat> chat_not_exist = chatRepository.findByUniqueChatId(123L);

        assertFalse(chat_exist.isEmpty());
        assertTrue(chat_not_exist.isEmpty());

        assertEquals(chat_exist.get().uniqueChatId(), 777L);
    }


    @Test
    void remove() {
        chatRepository.add(777L);
        Optional<Chat> chat = chatRepository.findByUniqueChatId(777L);
        assertFalse(chat.isEmpty());

        chatRepository.remove(chat.get().id());

        chat = chatRepository.findByUniqueChatId(777L);
        assertTrue(chat.isEmpty());
    }
}
