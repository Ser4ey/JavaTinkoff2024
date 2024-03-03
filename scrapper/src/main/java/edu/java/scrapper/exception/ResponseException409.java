package edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;

public class ResponseException409 extends CustomResponseException {
    public ResponseException409(String description, String exceptionMessage) {
        super(HttpStatus.CONFLICT, description, exceptionMessage);
    }
}
