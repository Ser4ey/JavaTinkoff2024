package edu.java.scrapper.response_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;


public record GitHubOwnerRepoResponse(
    Long id,
    String name,
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {

}

