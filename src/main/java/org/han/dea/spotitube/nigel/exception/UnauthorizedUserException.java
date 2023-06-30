package org.han.dea.spotitube.nigel.exception;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException() {
        super("Unauthorized user");
    }
}
