package edu.java.scrapper.model.dto;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    Integer size
) {
}
