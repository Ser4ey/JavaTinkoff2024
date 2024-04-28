package edu.java.scrapper.urls;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.urls.model.TrackedUrlInfo;
import edu.java.scrapper.urls.tracked_links.GitHubLink;
import edu.java.scrapper.urls.tracked_links.StackOverflowLink;
import edu.java.scrapper.urls.tracked_links.TrackedLink;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UrlsApiImplTest extends IntegrationTest {
    @Mock
    private GitHubLink gitHubLink;

    @Mock
    private StackOverflowLink stackOverflowLink;

    @InjectMocks
    private UrlsApiImpl urlsApiImpl;

    private static final OffsetDateTime CURRENT_TIME = OffsetDateTime.now();
    private static final TrackedUrlInfo TRACKED_URL_INFO = new TrackedUrlInfo(CURRENT_TIME, 5);

    @BeforeEach
    public void setUp() {
        Mockito.doReturn(true)
            .when(gitHubLink)
            .isCurrentLinkHost(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        Mockito.doReturn(false)
            .when(gitHubLink)
            .isCurrentLinkHost(URI.create("https://notgithub.com/Ser4ey/JavaTinkoff2024"));


        Mockito.doReturn(true)
            .when(stackOverflowLink)
            .isCurrentLinkHost(URI.create("https://stackoverflow.com/questions/41694969/how"));

        Mockito.doReturn(false)
            .when(stackOverflowLink)
            .isCurrentLinkHost(URI.create("https://notstackoverflow.com/questions/41694969/how"));

        // --------------

        Mockito.doReturn(Optional.of(TRACKED_URL_INFO))
            .when(gitHubLink)
            .getUrlInfo(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        Mockito.doReturn(Optional.empty())
            .when(gitHubLink)
            .getUrlInfo(URI.create("https://notgithub.com/Ser4ey/JavaTinkoff2024"));


        Mockito.doReturn(Optional.of(TRACKED_URL_INFO))
            .when(stackOverflowLink)
            .getUrlInfo(URI.create("https://stackoverflow.com/questions/41694969/how"));

        Mockito.doReturn(Optional.empty())
            .when(stackOverflowLink)
            .getUrlInfo(URI.create("https://notstackoverflow.com/questions/41694969/how"));

        // --------------

        List<TrackedLink> trackedLinks = Arrays.asList(gitHubLink, stackOverflowLink);
        ReflectionTestUtils.setField(urlsApiImpl, "trackedLinks", trackedLinks);
    }

    @Test
    void testGetUrlInfo() {
        var githubTrackedUrlInfo =
            urlsApiImpl.getUrlInfo(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        assertTrue(githubTrackedUrlInfo.isPresent());
        assertEquals(
            githubTrackedUrlInfo.get(),
            TRACKED_URL_INFO
        );

        var stackoverflowTrackedUrlInfo =
            urlsApiImpl.getUrlInfo(URI.create("https://stackoverflow.com/questions/41694969/how"));
        assertTrue(stackoverflowTrackedUrlInfo.isPresent());
        assertEquals(
            stackoverflowTrackedUrlInfo.get(),
            TRACKED_URL_INFO
        );

        assertFalse(urlsApiImpl.getUrlInfo(URI.create("https://gnotithub.com/Ser4ey/JavaTinkoff2024")).isPresent());
        assertFalse(urlsApiImpl.getUrlInfo(URI.create("https://notstackoverflow.com/questions/41694969/how")).isPresent());
        assertFalse(urlsApiImpl.getUrlInfo(URI.create("1")).isPresent());
    }

}
