package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.TrackNotDeletedException;

public class TrackNotDeletedExceptionMapper implements ExceptionMapper<TrackNotDeletedException> {

    @Override
    public Response toResponse(TrackNotDeletedException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
