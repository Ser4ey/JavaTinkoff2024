package edu.java.scrapper.urls;

import java.net.URI;
import java.time.OffsetDateTime;

public interface UrlsApi {
    boolean isWorkingUrl(URI url);

    OffsetDateTime getLastActivity(URI url);

}


