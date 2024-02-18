package edu.java.bot.urls;

import lombok.Getter;


@Getter
public abstract class TrackedUrl {
    private final String urlHost;

    TrackedUrl(String urlHost) {
        this.urlHost = urlHost;
    }

    public boolean isProperUrlHost(String url) {
        return urlHost.equals(UrlWorker.getHostFromUrl(url));
    }
}
