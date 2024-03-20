package edu.java.scrapper.urls;

import edu.java.scrapper.urls.tracked_links.TrackedLink;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UrlsApiImpl implements UrlsApi {
    private final Map<String, TrackedLink> trackedLinkMap;

    public UrlsApiImpl(ApplicationContext applicationContext) {
        this.trackedLinkMap = applicationContext.getBeansOfType(TrackedLink.class);
    }

    @Override
    public boolean isWorkingUrl(URI url) {
        for (String key : trackedLinkMap.keySet()) {
            if (!trackedLinkMap.get(key).isCurrentLinkHost(url)) {
                continue;
            }
            if (trackedLinkMap.get(key).isWorkingUrl(url)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Optional<OffsetDateTime> getLastActivity(URI url) {
        for (String key : trackedLinkMap.keySet()) {
            if (!trackedLinkMap.get(key).isCurrentLinkHost(url)) {
                continue;
            }
            return trackedLinkMap.get(key).getLastActivityTime(url);
        }

        return Optional.empty();
    }

}

