package edu.java.bot.model.dto;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdateRequest(
    Long id,
    @NotNull
    URI url,
    @NotNull
    String description,
    List<Long> tgChatIds
) {

}
