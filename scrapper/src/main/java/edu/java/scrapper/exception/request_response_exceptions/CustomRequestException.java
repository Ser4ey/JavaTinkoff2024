package edu.java.scrapper.exception.request_response_exceptions;

import edu.java.scrapper.model.dto.ApiErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomRequestException extends RuntimeException {
    private final ApiErrorResponse apiErrorResponse;
}

