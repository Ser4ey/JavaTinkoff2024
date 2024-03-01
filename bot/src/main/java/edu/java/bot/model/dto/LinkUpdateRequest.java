package edu.java.bot.model.dto;

import java.net.URI;

public record LinkUpdateRequest(
    Long id,
    URI url,
    String description,
    Long[] tgChatIds
) {

}
