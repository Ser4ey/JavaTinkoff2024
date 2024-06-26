package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JdbcLinkDAOTest extends IntegrationTest {
    private final JdbcChatDAO chatRepository;
    private final JdbcLinkDAO linkRepository;

    JdbcLinkDAOTest(@Autowired JdbcTemplate jdbcTemplate) {
        this.chatRepository = new JdbcChatDAO(jdbcTemplate);
        this.linkRepository = new JdbcLinkDAO(jdbcTemplate);
    }

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
    void testFindNotCheckedForLongTime() {
        var link1 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4"));
        var link2 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw5"));
        var link3 = linkRepository.addLink(URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw6"));
        linkRepository.updateLastCheckTime(link1.id(), OffsetDateTime.MAX);
        linkRepository.updateLastCheckTime(link2.id(), OffsetDateTime.MIN);
        link1 = linkRepository.findById(link1.id()).get();
        link2 = linkRepository.findById(link2.id()).get();
        link3 = linkRepository.findById(link3.id()).get();



        var links = linkRepository.findNotCheckedForLongTime(1);
        assertEquals(links.size(), 1);
        assertEquals(links.getFirst(), link2);

        links = linkRepository.findNotCheckedForLongTime(2);
        assertEquals(links.size(), 2);
        assertEquals(links.getFirst(), link2);
        assertEquals(links.getLast(), link3);

        links = linkRepository.findNotCheckedForLongTime(3);
        assertEquals(links.size(), 3);
        assertEquals(links.getFirst(), link2);
        assertEquals(links.getLast(), link1);
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
    void testUpdateCount() {
        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var added_link = linkRepository.addLink(uri);

        assertEquals(added_link.count(), 0);
        linkRepository.updateCount(added_link.id(), 7);

        var link = linkRepository.findByUrl(uri);
        assertEquals(link.get().count(), 7);
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

        var uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024");
        var added_link = linkRepository.addLink(uri);
        linkRepository.addLinkRelation(chatId, added_link.id());

        var links = linkRepository.findAllByChatId(chatId);
        assertFalse(links.isEmpty());
        Integer linkId = links.getFirst().id();

        linkRepository.removeLinkRelation(chatId, linkId);
        assertFalse(linkRepository.findAll().isEmpty());
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
        assertFalse(linkRepository.findAllByChatId(chatId1).isEmpty());
        assertFalse(linkRepository.findAllByChatId(chatId2).isEmpty());

        linkRepository.removeLinkRelation(chatId1, link.get().id());
        link = linkRepository.findByUrl(uri);
        assertFalse(link.isEmpty());
        assertFalse(linkRepository.findAll().isEmpty());
        assertTrue(linkRepository.findAllByChatId(chatId1).isEmpty());
        assertFalse(linkRepository.findAllByChatId(chatId2).isEmpty());

        linkRepository.removeLinkRelation(chatId2, link.get().id());
        link = linkRepository.findByUrl(uri);
        assertTrue(linkRepository.findAllByChatId(chatId1).isEmpty());
        assertTrue(linkRepository.findAllByChatId(chatId2).isEmpty());

        assertFalse(link.isEmpty());
        assertFalse(linkRepository.findAll().isEmpty());

        linkRepository.removeLinksWithNoRelations();

        link = linkRepository.findByUrl(uri);
        assertTrue(link.isEmpty());
        assertTrue(linkRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testRemoveLinksWithNoRelations() {
        Long chatId = 420L;
        chatRepository.add(chatId);

        var uri1 = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/1");
        var uri2 = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/2");
        var uri3 = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/3");

        linkRepository.addLink(uri1);
        linkRepository.addLink(uri2);
        linkRepository.addLink(uri3);

        assertEquals(linkRepository.findAll().size(), 3);
        linkRepository.removeLinksWithNoRelations();
        assertEquals(linkRepository.findAll().size(), 0);

        linkRepository.addLink(uri1);
        var link2 = linkRepository.addLink(uri2);
        linkRepository.addLink(uri3);
        linkRepository.addLinkRelation(chatId, link2.id());

        assertEquals(linkRepository.findAll().size(), 3);
        linkRepository.removeLinksWithNoRelations();
        assertEquals(linkRepository.findAll().size(), 1);

        var aliveLink = linkRepository.findByChatIdAndLinkId(chatId, link2.id());
        assertEquals(aliveLink.get().id(), link2.id());
        assertEquals(aliveLink.get().url(), link2.url());
        assertEquals(aliveLink.get().url(), uri2);

        assertEquals(linkRepository.findAllByChatId(chatId).size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void testAddLinkWithCount() {
        URI uri = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw4");
        Integer count = 7;

        var link1 = linkRepository.addLink(uri, count);
        assertEquals(link1.url(), uri);
        assertEquals(link1.count(), count);

        var link2 = linkRepository.findByUrl(uri);
        assertFalse(link2.isEmpty());
        assertEquals(link2.get().url(), uri);
        assertEquals(link2.get().count(), count);
    }

}

