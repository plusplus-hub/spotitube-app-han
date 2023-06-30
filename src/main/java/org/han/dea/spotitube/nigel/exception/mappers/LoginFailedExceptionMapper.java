package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.LoginFailedException;

public class LoginFailedExceptionMapper implements ExceptionMapper<LoginFailedException> {

    @Override
    public Response toResponse(LoginFailedException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
