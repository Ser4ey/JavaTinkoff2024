package edu.java.bot.urls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackedUrlTest {

    private final TrackedUrl trackedUrl = new TrackedUrl("www.tinkoff.ru") { };

    @Test
    public void testGetUrlHost() {
        assertEquals("www.tinkoff.ru", trackedUrl.getUrlHost());
    }

    @Test
    public void testIsProperUrlHost() {
        assertTrue(trackedUrl.isProperUrlHost("https://www.tinkoff.ru/career/"));
        assertFalse(trackedUrl.isProperUrlHost("https://mail.google.com/mail/u/0/#inbox"));
    }
}
