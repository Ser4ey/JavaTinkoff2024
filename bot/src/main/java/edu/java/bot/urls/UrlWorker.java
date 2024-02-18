package edu.java.bot.urls;

import java.net.URI;
import java.net.URL;

public class UrlWorker {
    private UrlWorker() {
        throw new AssertionError("UrlWorker cannot be instantiated");
    }

    public static boolean isValidUrl(String urlString) {
        try {
            URL url = new URI(urlString).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getHostFromUrl(String urlString) {
        try {
            URL url = new URI(urlString).toURL();
            return url.getHost();
        } catch (Exception e) {
            return null; // Если URL некорректен, вернуть null
        }
    }

    public static void main(String[] args) {
        System.out.println(
            getHostFromUrl("https://www.tinkoff.ru/career/")
        );
        System.out.println(
            getHostFromUrl("https://music.yandex.ru/album/29230920?utm_source=plus-home&utm_medium=news&utm_campaign=podcast-corn&shortlink=jpcafq1z&c=Podcast_Corn&pid=plus_home&deep_link_value=yandexmusic:/album/29230920&dir=desc&activeTab=about")
        );
    }

}
