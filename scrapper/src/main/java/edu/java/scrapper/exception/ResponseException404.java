package edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;

public class ResponseException404 extends CustomResponseException {
    public ResponseException404(String description, String exceptionMessage) {
        super(HttpStatus.NOT_FOUND, description, exceptionMessage);
    }
}
