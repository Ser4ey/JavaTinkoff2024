package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

        var link1 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.addLinkRelation(chatId, link1.id());

        var link2 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));
        linkRepository.addLinkRelation(chatId, link2.id());

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

        var link1 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        linkRepository.addLinkRelation(chatId1, link1.id());

        var link2 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));
        linkRepository.addLinkRelation(chatId2, link2.id());

        var links1 = linkRepository.findAllByChatId(chatId1);
        var links2 = linkRepository.findAllByChatId(chatId2);
        var links3 = linkRepository.findAllByChatId(chatId3);

        assertEquals(links1.size(), 1);
        assertEquals(links2.size(), 1);
        assertTrue(links3.isEmpty());

        assertEquals(links1.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4");
        assertEquals(links2.getFirst().url().toString(), "https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5");
    }

    @Test
    @Transactional
    @Rollback
    void testAddToManyChat() {
        Long chatId1 = 420L;
        chatRepository.add(chatId1);

        Long chatId2 = 3552L;
        chatRepository.add(chatId2);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId1, link.id());

        assertEquals(linkRepository.findAll().size(), 1);

        linkRepository.addLinkRelation(chatId2, link.id());

        assertEquals(linkRepository.findAll().size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void testFindById() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");

        var link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, link.id());

        var link_id = linkRepository.findByUrl(uri).get().id();

        var found_link = linkRepository.findById(link_id);
        assertFalse(found_link.isEmpty());
        assertEquals(found_link.get().url(), uri);
    }

    @Test
    @Transactional
    @Rollback
    void testFindByUrl() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");

        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, added_link.id());

        var link = linkRepository.findByUrl(uri);

        assertFalse(link.isEmpty());
        assertEquals(link.get().url(), uri);
    }

    @Test
    @Transactional
    @Rollback
    void testFindByChatIdAndLinkId() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");

        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, added_link.id());

        var link_id = linkRepository.findByUrl(uri).get().id();

        var link = linkRepository.findByChatIdAndLinkId(chatId, link_id);
        assertFalse(link.isEmpty());
        assertEquals(link.get().url(), uri);

        assertTrue(linkRepository.findByChatIdAndLinkId(-1L, link_id).isEmpty());
        assertTrue(linkRepository.findByChatIdAndLinkId(111L, link_id).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateLastUpdateTime() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, added_link.id());

        var link = linkRepository.findByUrl(uri);

        OffsetDateTime dateTime = OffsetDateTime.of(2000, 2, 20, 0, 0, 0, 0, ZoneOffset.UTC);
        linkRepository.updateLastUpdateTime(link.get().id(), dateTime);

        link = linkRepository.findByUrl(uri);
        assertEquals(link.get().lastUpdateTime(), dateTime);
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateLastCheckTime() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, added_link.id());

        var link = linkRepository.findByUrl(uri);

        OffsetDateTime dateTime = OffsetDateTime.of(2002, 2, 20, 0, 0, 0, 0, ZoneOffset.UTC);
        linkRepository.updateLastCheckTime(link.get().id(), dateTime);

        link = linkRepository.findByUrl(uri);
        assertEquals(link.get().lastCheckTime(), dateTime);
    }


    @Test
    @Transactional
    @Rollback
    void testRemove() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var added_link = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        linkRepository.addLinkRelation(chatId, added_link.id());

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

        var added_link = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        linkRepository.addLinkRelation(chatId, added_link.id());

        var links = linkRepository.findAllByChatId(chatId);
        assertFalse(links.isEmpty());
        Integer linkId = links.getFirst().id();

        linkRepository.removeLinkRelation(chatId, linkId);
        assertTrue(linkRepository.findAll().isEmpty());
        assertTrue(linkRepository.findAllByChatId(chatId).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testRemoveLinkRelation2() {
        Long chatId1 = 420L;
        Long chatId2 = 7532L;
        chatRepository.add(chatId1);
        chatRepository.add(chatId2);

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId1, added_link.id());
        linkRepository.addLinkRelation(chatId2, added_link.id());

        var link = linkRepository.findByUrl(uri);
        assertFalse(link.isEmpty());

        linkRepository.removeLinkRelation(chatId1, link.get().id());
        link = linkRepository.findByUrl(uri);
        assertFalse(link.isEmpty());
        assertFalse(linkRepository.findAll().isEmpty());


        linkRepository.removeLinkRelation(chatId2, link.get().id());
        link = linkRepository.findByUrl(uri);
        assertTrue(link.isEmpty());
        assertTrue(linkRepository.findAll().isEmpty());
    }


}

