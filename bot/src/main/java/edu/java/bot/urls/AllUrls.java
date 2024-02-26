package edu.java.bot.urls;

import java.util.LinkedHashSet;
import java.util.Set;

public class AllUrls {
    private AllUrls() {}

    private static final Set<String> URL_HOSTS = new LinkedHashSet<>();

    static {
        URL_HOSTS.add("github.com");
        URL_HOSTS.add("stackoverflow.com");
    }

    public static String getRegisteredUrl(String url) {
        // если ссылка зарегистрирована получаем её хост, в противном случае null
        boolean isValid = UrlWorker.isValidUrl(url);
        if (!isValid) {
            return null;
        }

        String urlHost = UrlWorker.getHostFromUrl(url);
        if (URL_HOSTS.contains(urlHost)) {
            return urlHost;
        }
        return null;
    }

    public static boolean isAllowedUrl(String url) {
        return getRegisteredUrl(url) != null;
    }
}

