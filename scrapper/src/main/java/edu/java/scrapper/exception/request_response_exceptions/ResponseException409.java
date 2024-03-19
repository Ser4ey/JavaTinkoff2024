package edu.java.scrapper.exception.request_response_exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException409 extends CustomResponseException {
    public ResponseException409(String description, String exceptionMessage) {
        super(HttpStatus.CONFLICT, description, exceptionMessage);
    }
}
