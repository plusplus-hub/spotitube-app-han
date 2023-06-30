package org.han.dea.spotitube.nigel.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.han.dea.spotitube.nigel.exception.PlaylistNotFoundException;

public class PlaylistNotFoundExceptionMapper implements ExceptionMapper<PlaylistNotFoundException> {

    @Override
    public Response toResponse(PlaylistNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
