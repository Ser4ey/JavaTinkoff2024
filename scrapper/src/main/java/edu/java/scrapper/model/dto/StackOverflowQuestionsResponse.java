package edu.java.scrapper.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionsResponse(
    @JsonProperty("items")
    List<StackOverflowQuestion> questions

) {
    public record StackOverflowQuestion(
        @JsonProperty("question_id")
        Long id,
        String title,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {

    }
}

