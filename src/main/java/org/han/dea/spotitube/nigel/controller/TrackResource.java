package org.han.dea.spotitube.nigel.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.han.dea.spotitube.nigel.service.IAuthService;
import org.han.dea.spotitube.nigel.service.ITrackService;

@Path("/tracks")
public class TrackResource {

    @Inject
    private IAuthService AUTH_SERVICE;
    @Inject
    private ITrackService TRACK_SERVICE;
    private static final String QUERY_PARAM_TOKEN_NAME = "token";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksNotInPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @QueryParam("forPlaylist") int playlistId) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok(TRACK_SERVICE.getAllTracksNotInPlaylist(playlistId, user.getId())).build();
    }

}