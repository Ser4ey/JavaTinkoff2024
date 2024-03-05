package edu.java.scrapper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class CustomResponseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String description;
    private final String exceptionMessage;
}
