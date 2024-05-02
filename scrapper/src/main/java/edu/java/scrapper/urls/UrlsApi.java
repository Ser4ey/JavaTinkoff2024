package edu.java.scrapper.urls;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.urls.model.TrackedUrlInfo;
import edu.java.scrapper.urls.model.UrlUpdateDto;
import java.net.URI;
import java.util.Optional;

public interface UrlsApi {
    Optional<TrackedUrlInfo> getUrlInfo(URI url);

    Optional<UrlUpdateDto> getUrlUpdate(Link link);

}

