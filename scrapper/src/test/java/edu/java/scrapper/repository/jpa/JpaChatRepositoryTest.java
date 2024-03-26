package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.entity.ChatEntity;
import edu.java.scrapper.model.entity.LinkEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

@SpringBootTest
class JpaChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    void testFindAll() {
        jpaChatRepository.save(
            new ChatEntity(1L)
        );
        jpaChatRepository.save(
            new ChatEntity(2L)
        );
        System.out.println(
            jpaChatRepository.findAll()
        );

    }

    @Test
    @Transactional
    @Rollback
    void testFindAll2() {
        var c = jpaChatRepository.save(
            new ChatEntity(1L)
        );

        var l = jpaLinkRepository.save(
            new LinkEntity("link_1", OffsetDateTime.now(), OffsetDateTime.now())
        );

//        linkRepository.addLinkRelation(c.getChatId(), l.getId());
//        linkRepository.findAll();

        c.getLinks().add(l);
        jpaChatRepository.save(c);

        l.getChats().add(c);
        jpaLinkRepository.save(l);


        System.out.println(jpaLinkRepository.findByUrl("link_1"));


        System.out.println(
            jpaChatRepository.findAll()
        );

        System.out.println(
            jpaLinkRepository.findAll()
        );

    }
}
