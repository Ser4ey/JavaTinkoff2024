package edu.java.scrapper.exception.request_response_exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException404 extends CustomResponseException {
    public ResponseException404(String description, String exceptionMessage) {
        super(HttpStatus.NOT_FOUND, description, exceptionMessage);
    }
}
