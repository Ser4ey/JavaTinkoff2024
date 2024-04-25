package edu.java.scrapper.urls;

import java.time.OffsetDateTime;

public record UrlUpdateDto(
    String updateText,
    OffsetDateTime newLastActivity,
    Integer newCount
) {

}
