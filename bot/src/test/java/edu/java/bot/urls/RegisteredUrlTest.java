package edu.java.bot.urls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredUrlTest {

    @Test
    void testGetRegisteredUrl() {
        assertEquals(
            RegisteredUrl.GitHub_Url.getTrackedUrl().getClass(), GitHubUrl.class
        );

        assertEquals(
            RegisteredUrl.StackOverflow_Url.getTrackedUrl().getClass(), StackOverflowUrl.class
        );
    }

    @Test
    void testGetTrackedUrl() {
        assertEquals(
            RegisteredUrl.getRegisteredUrl("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1"),
            RegisteredUrl.GitHub_Url
        );

        assertEquals(
            RegisteredUrl.getRegisteredUrl("https://stackoverflow.com/questions/49031070/how-can-i-supress-the-checkstyle-message-utility-classes-should-not-have-a-publ"),
            RegisteredUrl.StackOverflow_Url
        );
    }

    @Test
    void testGetTrackedUrlNull() {
        assertNull(
            RegisteredUrl.getRegisteredUrl(null)
        );

        assertNull(
            RegisteredUrl.getRegisteredUrl("123")
        );

        assertNull(
            RegisteredUrl.getRegisteredUrl("https://www.vogella.com/tutorials/JUnit/article.html")
        );
    }
}
