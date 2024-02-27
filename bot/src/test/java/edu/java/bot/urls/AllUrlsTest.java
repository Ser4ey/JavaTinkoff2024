package edu.java.bot.urls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllUrlsTest {

    @Test
    void testGetRegisteredUrl() {
        assertEquals(
            AllUrls.getRegisteredUrl("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw1"),
            "github.com"
        );

        assertEquals(
            AllUrls.getRegisteredUrl("https://stackoverflow.com/questions/49031070/how-can-i-supress-the-checkstyle-message-utility-classes-should-not-have-a-publ"),
            "stackoverflow.com"
        );
    }

    @Test
    void testGetRegisteredUrlNull() {
        assertNull(AllUrls.getRegisteredUrl(null));

        assertNull(AllUrls.getRegisteredUrl("123"));

        assertNull(AllUrls.getRegisteredUrl("https://www.vogella.com/tutorials/JUnit/article.html"));
    }

    @Test
    void testIsAllowedUrl() {
        assertFalse(AllUrls.isAllowedUrl(null));
        assertFalse(AllUrls.isAllowedUrl("123"));
        assertFalse(AllUrls.isAllowedUrl("https://javarush.com/groups/posts/spring-framework-java-1"));

        assertTrue(AllUrls.isAllowedUrl("https://github.com/Ser4ey/JavaTinkoff2024/pull/1"));
        assertTrue(AllUrls.isAllowedUrl("https://stackoverflow.com/questions/"));
    }
}
