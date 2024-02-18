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
        boolean isValid = UrlWorker.isValidUrl(url);
        if (!isValid) {
            return null;
        }

        for (RegisteredUrl regUrl : RegisteredUrl.values()) {
            if (regUrl.getTrackedUrl().isProperUrlHost(url)) {
                return regUrl;
            }
        }

        return null;
    }
}
