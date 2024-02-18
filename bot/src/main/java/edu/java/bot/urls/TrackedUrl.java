package edu.java.bot.urls;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class TrackedUrl {
    private final String urlHost;

    TrackedUrl(String urlHost) {
        this.urlHost = urlHost;
    }

    public boolean isProperUrlHost(String url) {
        return Objects.equals(UrlWorker.getHostFromUrl(url), urlHost);
    }
}
