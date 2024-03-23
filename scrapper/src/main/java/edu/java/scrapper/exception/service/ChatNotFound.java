package edu.java.scrapper.exception.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatNotFound extends RuntimeException {
    // чат не зарегистрирован в бд
    private final String exceptionMessage;

}
