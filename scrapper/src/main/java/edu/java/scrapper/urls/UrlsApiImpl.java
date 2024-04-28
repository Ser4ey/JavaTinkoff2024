package edu.java.scrapper.urls;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.urls.model.UrlUpdateDto;
import edu.java.scrapper.urls.tracked_links.TrackedLink;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class UrlsApiImpl implements UrlsApi {

    private final List<TrackedLink> trackedLinks;

    @Override
    public boolean isWorkingUrl(URI url) {
        for (TrackedLink tackedLink : trackedLinks) {
            if (!tackedLink.isCurrentLinkHost(url)) {
                continue;
            }
            if (tackedLink.isWorkingUrl(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<UrlUpdateDto> getUrlUpdate(Link link) {
        for (TrackedLink tackedLink : trackedLinks) {
            if (!tackedLink.isCurrentLinkHost(link.url())) {
                continue;
            }
            return tackedLink.getUpdate(link);
        }
        log.warn("Неизвестная ссылка: {}", link.url());
        return Optional.empty();
    }

}

