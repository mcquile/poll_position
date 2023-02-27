package org.example.exceptions;

public class NoAuthorisationHeaderException extends Exception {
    public NoAuthorisationHeaderException() {
        super("No authorisation header present in request");
    }
}

