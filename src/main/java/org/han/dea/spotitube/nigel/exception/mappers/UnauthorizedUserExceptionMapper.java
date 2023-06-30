package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.UnauthorizedUserException;

public class UnauthorizedUserExceptionMapper implements ExceptionMapper<UnauthorizedUserException> {

    @Override
    public Response toResponse(UnauthorizedUserException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
