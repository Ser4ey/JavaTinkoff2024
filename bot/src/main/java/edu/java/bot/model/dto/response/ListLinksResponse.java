package edu.java.bot.model.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ListLinksResponse(
    @NotNull
    List<LinkResponse> links,
    Integer size
) {
}
