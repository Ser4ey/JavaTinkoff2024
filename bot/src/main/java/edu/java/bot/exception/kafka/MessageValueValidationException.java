package edu.java.bot.exception.kafka;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageValueValidationException extends RuntimeException {
    private final String validationProblem;
}
