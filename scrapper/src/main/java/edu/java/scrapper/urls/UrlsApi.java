package edu.java.scrapper.urls;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface UrlsApi {
    boolean isWorkingUrl(URI url);

    Optional<OffsetDateTime> getLastActivity(URI url);

}


