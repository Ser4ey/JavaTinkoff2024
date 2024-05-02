package edu.java.scrapper.urls.model;

import java.time.OffsetDateTime;

public record TrackedUrlInfo(
    OffsetDateTime lastActivity,
    Integer count
) {
}
