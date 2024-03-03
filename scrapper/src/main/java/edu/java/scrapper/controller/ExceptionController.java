package edu.java.scrapper.controller;

import edu.java.scrapper.exception.CustomResponseException;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiErrorResponse(
                "Validation failed",
                "400",
                ex.getClass().getName(),
                ex.getMessage(),
                stacktrace)
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiErrorResponse(
                "Unhandled server error",
                "500",
                ex.getClass().getName(),
                ex.getMessage(),
                stacktrace)
            );
    }
}
