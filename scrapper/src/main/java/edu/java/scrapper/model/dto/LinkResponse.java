package edu.java.scrapper.model.dto;

import java.net.URI;

public record LinkResponse(
    Long id,
    URI url
) {

}

