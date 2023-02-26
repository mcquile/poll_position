package org.example.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String property) {
        super(String.format("A user attribute %s already exists.", property));
    }
}

