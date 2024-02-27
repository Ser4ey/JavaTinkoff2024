package edu.java.bot.urls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UrlWorkerTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "https://edu.tinkoff.ru/",
        "https://github.com/pengrad/java-telegram-bot-api",
        "https://stackoverflow.co/advertising/employer-branding/",
        "https://music.yandex.ru/album/29230920?utm_source=plus-home&utm_medium=news&utm_campaign=podcast-corn&shortlink=jpcafq1z&c=Podcast_Corn&pid=plus_home&deep_link_value=yandexmusic:/album/29230920&dir=desc&activeTab=about",
        "https://stackoverflow.com/questions/43760833/how-to-loop-java-code"
    })
    void testIsValidUrlTrue(String url) {
        assertTrue(UrlWorker.isValidUrl(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "httOLEGps://edu.tinkoff.ru/",
        "12345",
        "htps://stackoverflow.co/advertising/employer-branding/",
        "",
        "https2stackoverflow_com/questions/43760833/how-to-loop-java-code"
    })
    @NullSource
    void testIsValidUrlFalse(String url) {
        assertFalse(UrlWorker.isValidUrl(url));
    }

    @Test
    void testGetHostFromUrl() {
        assertEquals(
            UrlWorker.getHostFromUrl("https://stackoverflow.com/questions/43760833/how-to-loop-java-code"),
            "stackoverflow.com"
        );

        assertEquals(
            UrlWorker.getHostFromUrl("https://github.com/Ser4ey"),
            "github.com"
        );

        assertEquals(
            UrlWorker.getHostFromUrl("https://assertj.github.io/doc/#assertj-guava"),
            "assertj.github.io"
        );

        assertNull(
            UrlWorker.getHostFromUrl("Tinkoff"),
            "assertj.github.io"
        );

        assertNull(
            UrlWorker.getHostFromUrl(null),
            "assertj.github.io"
        );
    }
}

