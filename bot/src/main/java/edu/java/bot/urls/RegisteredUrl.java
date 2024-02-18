package edu.java.bot.urls;

import lombok.Getter;


@Getter
public enum RegisteredUrl {
    GitHub_Url(new GitHubUrl()),
    StackOverflow_Url(new StackOverflowUrl());

    private final TrackedUrl trackedUrl;

    RegisteredUrl(TrackedUrl trackedUrl) {
        this.trackedUrl = trackedUrl;
    }

    public static RegisteredUrl getRegisteredUrl(String url) {
        String urlHost = UrlWorker.getHostFromUrl(url);
        if (urlHost == null) {
            return null;
        }

        for (RegisteredUrl regUrl : RegisteredUrl.values()) {
            if (urlHost.equals(regUrl.getTrackedUrl().getUrlHost())){
                return regUrl;
            }
        }

        return null;
    }
}
