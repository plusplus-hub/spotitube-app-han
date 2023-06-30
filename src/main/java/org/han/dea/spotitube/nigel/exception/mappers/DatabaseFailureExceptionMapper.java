package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;

public class DatabaseFailureExceptionMapper implements ExceptionMapper<DatabaseFailureException> {

    @Override
    public Response toResponse(DatabaseFailureException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
