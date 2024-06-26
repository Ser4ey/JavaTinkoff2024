package edu.java.scrapper.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;


public record GitHubOwnerRepoResponse(
    Long id,
    String name,
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,
    @JsonProperty("forks_count")
    Integer forksCount
) {

}

