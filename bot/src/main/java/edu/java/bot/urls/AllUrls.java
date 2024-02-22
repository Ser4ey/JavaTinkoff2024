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

    private static boolean isProperUrlHost(String urlHost, String url) {
        // Имеет ли ссылка url хост urlHost (https://github.com/Ser4ey/JavaTinkoff2024/pull/1 -> github.com)
        return urlHost.equals(UrlWorker.getHostFromUrl(url));
    }

    public static String getRegisteredUrl(String url) {
        boolean isValid = UrlWorker.isValidUrl(url);
        if (!isValid) {
            return null;
        }

        for (String urlHost : URL_HOSTS.toArray(new String[0])) {
            if (isProperUrlHost(urlHost, url)) {
                return urlHost;
            }
        }

        return null;
    }

    public static boolean isAllowedUrl(String url) {
        return getRegisteredUrl(url) != null;
    }
}

