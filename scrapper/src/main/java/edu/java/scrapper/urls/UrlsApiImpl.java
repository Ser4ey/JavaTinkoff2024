package edu.java.scrapper.urls;

import edu.java.scrapper.urls.tracked_links.TrackedLink;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
    public Optional<OffsetDateTime> getLastActivity(URI url) {
        for (TrackedLink tackedLink : trackedLinks) {
            if (!tackedLink.isCurrentLinkHost(url)) {
                continue;
            }
            return tackedLink.getLastActivityTime(url);

        }
        return Optional.empty();
    }

}

