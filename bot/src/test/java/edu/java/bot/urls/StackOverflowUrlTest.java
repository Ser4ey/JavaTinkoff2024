package edu.java.bot.urls;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StackOverflowUrlTest {
    private final StackOverflowUrl stackOverflowUrl = new StackOverflowUrl();

    @Test
    void testGetUrlHost() {
        assertEquals(stackOverflowUrl.getUrlHost(), "stackoverflow.com");
    }
}
