package edu.java.bot.exception.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScrapperException extends RuntimeException {
    private final String statusCode;
    private final String description;
    private final String exceptionMessage;
}
