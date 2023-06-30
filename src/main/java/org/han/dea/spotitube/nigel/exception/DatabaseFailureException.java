package org.han.dea.spotitube.nigel.exception;

public class DatabaseFailureException extends RuntimeException {
    public DatabaseFailureException() {
        super("Something went wrong while communicating with the database");
    }
    public DatabaseFailureException(String message) {
        super(message);
    }
}
