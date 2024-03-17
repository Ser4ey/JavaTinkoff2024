package edu.java.scrapper.model;

import java.net.URI;
import java.time.OffsetDateTime;



public record Link(
    Integer id,
    URI url,
    OffsetDateTime lastCheckTime
) {

}
