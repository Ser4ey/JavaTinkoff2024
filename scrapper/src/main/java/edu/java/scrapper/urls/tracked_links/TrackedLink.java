package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.urls.model.TrackedUrlInfo;
import edu.java.scrapper.urls.model.UrlUpdateDto;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

public interface TrackedLink {
    boolean isCurrentLinkHost(URI url); // ссылка относится к текущему адресу

    Optional<TrackedUrlInfo> getUrlInfo(URI url); // актуальная информация по ссылке

    Optional<UrlUpdateDto> getUpdate(Link link); // обновление источника (если есть)

    static Optional<String> getHostFromUrl(URI uri) {
        try {
            URL url = uri.toURL();
            return Optional.of(url.getHost());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
