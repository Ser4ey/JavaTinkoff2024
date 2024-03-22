package edu.java.bot.exception.request;

import edu.java.bot.model.dto.ApiErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;
}

