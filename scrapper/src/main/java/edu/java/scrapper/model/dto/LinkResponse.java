package edu.java.scrapper.model.dto;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    Long id,
    @NotNull
    URI url
) {

}

