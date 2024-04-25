package edu.java.scrapper.urls;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.urls.tracked_links.GitHubLink;
import edu.java.scrapper.urls.tracked_links.StackOverflowLink;
import edu.java.scrapper.urls.tracked_links.TrackedLink;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
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
    private static final UrlUpdateDto urlUpdateDto = new UrlUpdateDto("text", CURRENT_TIME, 0);


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
        Mockito.doReturn(true)
            .when(gitHubLink)
            .isWorkingUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));

        Mockito.doReturn(false)
            .when(gitHubLink)
            .isWorkingUrl(URI.create("https://notgithub.com/Ser4ey/JavaTinkoff2024"));


        Mockito.doReturn(true)
            .when(stackOverflowLink)
            .isWorkingUrl(URI.create("https://stackoverflow.com/questions/41694969/how"));

        Mockito.doReturn(false)
            .when(stackOverflowLink)
            .isWorkingUrl(URI.create("https://notstackoverflow.com/questions/41694969/how"));

        // --------------

        List<TrackedLink> trackedLinks = Arrays.asList(gitHubLink, stackOverflowLink);
        ReflectionTestUtils.setField(urlsApiImpl, "trackedLinks", trackedLinks);
    }


    @Test
    void testIsWorkingUrl() {
        assertTrue(urlsApiImpl.isWorkingUrl(URI.create("https://github.com/Ser4ey/JavaTinkoff2024")));
        assertTrue(urlsApiImpl.isWorkingUrl(URI.create("https://stackoverflow.com/questions/41694969/how")));

        assertFalse(urlsApiImpl.isWorkingUrl(URI.create("https://gnotithub.com/Ser4ey/JavaTinkoff2024")));
        assertFalse(urlsApiImpl.isWorkingUrl(URI.create("https://notstackoverflow.com/questions/41694969/how")));
        assertFalse(urlsApiImpl.isWorkingUrl(URI.create("1")));
    }

}
