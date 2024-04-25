package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.response.GitHubOwnerRepoResponse;
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
class GitHubLinkTest extends IntegrationTest {
    @Mock
    private GitHubClient gitHubClient;

    @InjectMocks
    private GitHubLink gitHubLink;

    @Test
    void testIsCurrentLinkHost() {
        var goodURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var badURI = URI.create("https://notgithub.com/Ser4ey/JavaTinkoff2024/pull/6");

        assertTrue(gitHubLink.isCurrentLinkHost(goodURI));
        assertFalse(gitHubLink.isCurrentLinkHost(badURI));
    }

    @Test
    void testIsWorkingUrl() {
        OffsetDateTime now = OffsetDateTime.now();
        GitHubOwnerRepoResponse gitHubOwnerRepoResponse = new GitHubOwnerRepoResponse(
            10L,
            "TinkoffJava2024",
            OffsetDateTime.MIN,
            now,
            0
            );


        Mockito.doReturn(gitHubOwnerRepoResponse)
            .when(gitHubClient)
            .getRepository("Ser4ey", "JavaTinkoff2024");

        Mockito.doReturn(null)
            .when(gitHubClient)
            .getRepository("Ser4ey", "NotJavaTinkoff2024");


        var goodURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var badURI = URI.create("https://github.com/Ser4ey/NotJavaTinkoff2024/pull/6");
        var veryBadURI = URI.create("1");

        assertTrue(gitHubLink.isWorkingUrl(goodURI));
        assertFalse(gitHubLink.isWorkingUrl(badURI));
        assertFalse(gitHubLink.isWorkingUrl(veryBadURI));
    }

    @Test
    void testGetUpdate() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime tomorrow = now.plusDays(1);

        var gitHubOwnerRepoResponse = new GitHubOwnerRepoResponse(
            10L,
            "TinkoffJava2024",
            tomorrow,
            tomorrow,
            0
        );

        Mockito.doReturn(gitHubOwnerRepoResponse)
            .when(gitHubClient)
            .getRepository("Ser4ey", "JavaTinkoff2024");

        Mockito.doReturn(null)
            .when(gitHubClient)
            .getRepository("Ser4ey", "NotJavaTinkoff2024");


        var goodURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var badURI = URI.create("https://github.com/Ser4ey/NotJavaTinkoff2024/pull/6");

        var none = gitHubLink.getUpdate(
            new Link(3, badURI, now, now, 10)
        );
        assertTrue(none.isEmpty());

        var some = gitHubLink.getUpdate(
            new Link(10, goodURI, now, now, 10)
        );

        assertFalse(some.isEmpty());
        assertEquals(some.get().newCount(), 0);

        System.out.println(some.get().newLastActivity());
        System.out.println(now);
        assertTrue(some.get().newLastActivity().isEqual(tomorrow));
    }
}
