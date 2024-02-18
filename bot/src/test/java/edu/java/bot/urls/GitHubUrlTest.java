package edu.java.bot.urls;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GitHubUrlTest {
    private final GitHubUrl gitHubUrl = new GitHubUrl();

    @Test
    void testGetUrlHost() {
        assertEquals(gitHubUrl.getUrlHost(), "github.com");
    }
}
