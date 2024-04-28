package edu.java.scrapper.urls.model;

import java.time.OffsetDateTime;

public record UrlUpdateDto(
    String updateText,
    OffsetDateTime newLastActivity,
    Integer newCount
) {

}
