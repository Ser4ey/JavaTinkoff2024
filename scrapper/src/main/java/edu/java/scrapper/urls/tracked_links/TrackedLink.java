package edu.java.scrapper.urls.tracked_links;

import java.net.URI;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface TrackedLink {
    boolean isCurrentLinkHost(URI url); // ссылка относится к текущему адресу

    boolean isWorkingUrl(URI url); // ссылка доступна для получения обновлений

    Optional<OffsetDateTime> getLastActivityTime(URI url); // время последнего обновления

    static Optional<String> getHostFromUrl(URI uri) {
        try {
            URL url = uri.toURL();
            return Optional.of(url.getHost());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
