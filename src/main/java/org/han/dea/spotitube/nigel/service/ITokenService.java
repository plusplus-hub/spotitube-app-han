package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;

public interface ITokenService {

    TokenDTO getToken(TokenDTO token);

    boolean saveToken(TokenDTO token);

    TokenDTO createToken(UserDTO userDTO);

}
