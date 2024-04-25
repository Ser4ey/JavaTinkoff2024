package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.response.StackOverflowQuestionsResponse.StackOverflowQuestion;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class StackOverflowLinkTest extends IntegrationTest {
    @Mock
    private StackOverflowClient stackOverflowClient;

    @InjectMocks
    private StackOverflowLink stackOverflowLink;

    @Test
    void testIsCurrentLinkHost() {
        var goodURI = URI.create("https://stackoverflow.com/questions/41694969/how-to-replace-an-existing-bean-in-spring-boot-application");
        var badURI = URI.create("https://notstackoverflow.com/questions/41694969/how-to-replace-an-existing-bean-in-spring-boot-application");

        assertTrue(stackOverflowLink.isCurrentLinkHost(goodURI));
        assertFalse(stackOverflowLink.isCurrentLinkHost(badURI));
    }

    @Test
    void testIsWorkingUrl() {
        OffsetDateTime now = OffsetDateTime.now();
        var StackOverflowQuestion = new StackOverflowQuestion (
            10L,
            "TinkoffJava2024",
            now,
            0
        );

        Mockito.doReturn(StackOverflowQuestion)
            .when(stackOverflowClient)
            .getQuestion(41694969L);

        Mockito.doReturn(null)
            .when(stackOverflowClient)
            .getQuestion(1L);

        var goodURI = URI.create("https://stackoverflow.com/questions/41694969/how-to-replace-an-existing-bean-in-spring-boot-application");
        var badURI = URI.create("https://notstackoverflow.com/questions/1/how-to-replace-an-existing-bean-in-spring-boot-application");

        assertTrue(stackOverflowLink.isWorkingUrl(goodURI));
        assertFalse(stackOverflowLink.isWorkingUrl(badURI));
    }

    @Test
    void testGetUpdate() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime tomorrow = now.plusDays(1);

        var StackOverflowQuestion = new StackOverflowQuestion (
            10L,
            "TinkoffJava2024 the best",
            tomorrow,
            100
        );

        Mockito.doReturn(StackOverflowQuestion)
            .when(stackOverflowClient)
            .getQuestion(41694969L);

        Mockito.doReturn(null)
            .when(stackOverflowClient)
            .getQuestion(1L);

        var goodURI = URI.create("https://stackoverflow.com/questions/41694969/how-to-replace-an-existing-bean-in-spring-boot-application");
        var badURI = URI.create("https://notstackoverflow.com/questions/1/how-to-replace-an-existing-bean-in-spring-boot-application");

        var none = stackOverflowLink.getUpdate(
            new Link(5, badURI, now, now, 10)
        );
        assertTrue(none.isEmpty());

        var some = stackOverflowLink.getUpdate(
            new Link(10, goodURI, now, now, 10)
        );
        assertFalse(some.isEmpty());
        assertEquals(some.get().newCount(), 100);
        assertTrue(some.get().newLastActivity().isEqual(tomorrow));
    }

}

