package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.model.UserDTO;

public interface IAuthService {
    boolean validatePassword(UserDTO user);
    UserDTO getUserByToken(String token);
}
