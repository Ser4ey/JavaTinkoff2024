package edu.java.scrapper.controller;

import edu.java.scrapper.exception.request_response_exceptions.CustomResponseException;
import edu.java.scrapper.exception.service.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service.ChatNotFound;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomResponseException(CustomResponseException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            ex.getDescription(),
            String.valueOf(ex.getHttpStatus().value()),
            ex.getClass().getName(),
            ex.getExceptionMessage(),
            stacktrace
        );

        return new ResponseEntity<>(apiErrorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        HandlerMethodValidationException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
                    "Validation failed",
                    "400",
                    ex.getClass().getName(),
                    ex.getMessage(),
                    stacktrace
            );
    }
    // ---

    @ExceptionHandler(ChatAlreadyRegistered.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiErrorResponse handleChatAlreadyRegistered(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "The chat is already registered",
            "409",
            ex.getClass().getName(),
            "You cannot register a chat 2 times in a row",
            stacktrace
        );
    }

    @ExceptionHandler(ChatNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleChatNotFound(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "The chat was not found",
            "404",
            ex.getClass().getName(),
            "You can't delete something that doesn't exist",
            stacktrace
        );
    }


    // ---

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
                    "Unhandled server error",
                    "500",
                    ex.getClass().getName(),
                    ex.getMessage(),
                    stacktrace
            );
    }
}
