package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.TrackNotAddedException;

public class TrackNotAddedExceptionMapper implements ExceptionMapper<TrackNotAddedException> {
    @Override
    public Response toResponse(TrackNotAddedException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
