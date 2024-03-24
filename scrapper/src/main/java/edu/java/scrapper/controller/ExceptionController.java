package edu.java.scrapper.controller;

import edu.java.scrapper.exception.service.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service.ChatNotFound;
import edu.java.scrapper.exception.service.LinkAlreadyTracking;
import edu.java.scrapper.exception.service.LinkDoNotWorking;
import edu.java.scrapper.exception.service.LinkNotFound;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@SuppressWarnings("MultipleStringLiterals")
public class ExceptionController {
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
    public ApiErrorResponse handleChatNotFound(ChatNotFound ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "The chat was not found",
            "404",
            ex.getClass().getName(),
            ex.getExceptionMessage(),
            stacktrace
        );
    }

    // ---

    @ExceptionHandler(LinkAlreadyTracking.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiErrorResponse handleLinkAlreadyTracking(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "This link is already registered",
            "409",
            ex.getClass().getName(),
            "You cannot register a link 2 times in a row",
            stacktrace
        );
    }

    @ExceptionHandler(LinkNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleLinkNotFound(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "This link is not tracked. You can't delete something that doesn't exist.",
            "404",
            ex.getClass().getName(),
            "You can't delete something that doesn't exist",
            stacktrace
        );
    }

    @ExceptionHandler(LinkDoNotWorking.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleLinkDoNotWorking(Exception ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();

        return new ApiErrorResponse(
            "The link didn't pass the work check",
            "400",
            ex.getClass().getName(),
            "The link is not available for updating via the API. Check that the link is correct.",
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
