package edu.java.scrapper.exception.request_response_exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException400 extends CustomResponseException {
    public ResponseException400(String description, String exceptionMessage) {
        super(HttpStatus.BAD_REQUEST, description, exceptionMessage);
    }
}
