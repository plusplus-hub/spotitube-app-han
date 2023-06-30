package org.han.dea.spotitube.nigel.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.TrackDTO;
import org.han.dea.spotitube.nigel.service.IPlaylistService;
import org.han.dea.spotitube.nigel.service.IAuthService;
import org.han.dea.spotitube.nigel.service.ITrackService;


@Path("/playlists")
public class PlaylistResource {

    @Inject
    private IPlaylistService PLAY_SERVICE;
    @Inject
    private ITrackService TRACK_SERVICE;
    @Inject
    private IAuthService AUTH_SERVICE;

    private static final String QUERY_PARAM_TOKEN_NAME = "token";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok(PLAY_SERVICE.getAllPlaylists(user)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, PlaylistDTO playlist) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.status(Response.Status.CREATED).entity(PLAY_SERVICE.createPlaylist(user, playlist)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @PathParam("id") int playlistId) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok().entity(PLAY_SERVICE.deletePlaylist(user, playlistId)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @PathParam("id") int playlistId, PlaylistDTO playlist) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok().entity(PLAY_SERVICE.updatePlaylist(user, playlistId, playlist)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response getAllTracksFromPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @PathParam("playlistId") int playlistId) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok(TRACK_SERVICE.getAllTracksFromPlaylist(playlistId, user.getId())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response addTrackToPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @PathParam("playlistId") int playlistId, TrackDTO track) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.status(Response.Status.CREATED).entity(TRACK_SERVICE.addTrackToPlaylist(playlistId, track, user.getId())).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@QueryParam(QUERY_PARAM_TOKEN_NAME) String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        var user = AUTH_SERVICE.getUserByToken(token);
        return Response.ok().entity(TRACK_SERVICE.deleteTrackFromPlaylist(playlistId, trackId, user.getId())).build();
    }
}
