package org.example.exceptions;

public class NoAuthorisationHeaderException extends RuntimeException {
    public NoAuthorisationHeaderException() {
        super("No authorisation header present in request");
    }
}

