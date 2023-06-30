package org.han.dea.spotitube.nigel.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.service.ITokenService;
import org.han.dea.spotitube.nigel.service.IAuthService;


@Path("/login")
public class LoginResource {

    @Inject
    private IAuthService userService;
    @Inject
    private ITokenService tokenService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user) {
        boolean authorized = userService.validatePassword(user);
        return authorized ? Response.ok(tokenService.createToken(user)).build() : Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
