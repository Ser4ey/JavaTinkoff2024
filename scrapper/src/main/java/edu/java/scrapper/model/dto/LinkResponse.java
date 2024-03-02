package edu.java.scrapper.model.dto;

import java.net.URI;
import java.util.List;

public record LinkResponse(
    Long id,
    URI url
) {

}

