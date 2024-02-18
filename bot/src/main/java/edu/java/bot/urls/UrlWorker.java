package edu.java.bot.urls;

import java.net.URI;
import java.net.URL;

public class UrlWorker {
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

}
