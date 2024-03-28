package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.entity.LinkEntity;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpaLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    void testFindById() {

        jpaLinkRepository.save(
            new LinkEntity("link_1")
        );


        jpaLinkRepository.save(
            new LinkEntity("link_2")
        );

        System.out.println(jpaLinkRepository.findAll());

        System.out.println(
            jpaLinkRepository.findByUrl("link_1")
        );
    }
}
