package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.model.dto.GitHubOwnerRepoResponse;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class GitHubLinkTest extends IntegrationTest {
    @Mock
    private GitHubClient gitHubClient;

    @InjectMocks
    private GitHubLink gitHubLink;

    @BeforeAll
    static void testSetUp() {
    }

    @Test
    void testIsCurrentLinkHost() {
        var gitHubURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var notGitHubURI = URI.create("https://notgithub.com/Ser4ey/JavaTinkoff2024/pull/6");

        assertTrue(gitHubLink.isCurrentLinkHost(gitHubURI));
        assertFalse(gitHubLink.isCurrentLinkHost(notGitHubURI));
    }

    @Test
    void testIsWorkingUrl() {
        OffsetDateTime now = OffsetDateTime.now();
        GitHubOwnerRepoResponse gitHubOwnerRepoResponse = new GitHubOwnerRepoResponse(
            10L,
            "TinkoffJava2024",
            OffsetDateTime.MIN,
            now
            );


        Mockito.doReturn(gitHubOwnerRepoResponse)
            .when(gitHubClient)
            .getRepository("Ser4ey", "JavaTinkoff2024");

        Mockito.doReturn(null)
            .when(gitHubClient)
            .getRepository("Ser4ey", "NotJavaTinkoff2024");


        var GoodURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var BadURI = URI.create("https://github.com/Ser4ey/NotJavaTinkoff2024/pull/6");
        var VeryBadURI = URI.create("1");

        assertTrue(gitHubLink.isWorkingUrl(GoodURI));
        assertFalse(gitHubLink.isWorkingUrl(BadURI));
        assertFalse(gitHubLink.isWorkingUrl(VeryBadURI));
    }

    @Test
    void testGetLastActivityTime() {
        OffsetDateTime now = OffsetDateTime.now();
        GitHubOwnerRepoResponse gitHubOwnerRepoResponse = new GitHubOwnerRepoResponse(
            10L,
            "TinkoffJava2024",
            OffsetDateTime.MIN,
            now
        );


        Mockito.doReturn(gitHubOwnerRepoResponse)
            .when(gitHubClient)
            .getRepository("Ser4ey", "JavaTinkoff2024");

        Mockito.doReturn(null)
            .when(gitHubClient)
            .getRepository("Ser4ey", "NotJavaTinkoff2024");


        var GoodURI = URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6");
        var BadURI = URI.create("https://github.com/Ser4ey/NotJavaTinkoff2024/pull/6");

        var time = gitHubLink.getLastActivityTime(GoodURI);
        assertEquals(time.get(), now);

        var time2 = gitHubLink.getLastActivityTime(BadURI);
        assertTrue(time2.isEmpty());
    }
}
