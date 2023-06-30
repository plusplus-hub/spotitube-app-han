package org.han.dea.spotitube.nigel.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Can''t find user or password is incorrect");
    }
}
