package org.example.exceptions;

public class InvalidLoginCredentialsException extends Exception {
    public InvalidLoginCredentialsException() {
        super("Invalid login credentials provided");
    }
}