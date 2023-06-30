package org.han.dea.spotitube.nigel.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.persistence.dao.ITokenDAO;
import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;

import java.util.UUID;


public class TokenService implements ITokenService {

    private final ITokenDAO TOKEN_DAO;


    @Inject
    public TokenService (@Named("tokenDAO") ITokenDAO tokenDAO) {
        this.TOKEN_DAO = tokenDAO;
    }

    @Override
    public TokenDTO getToken(TokenDTO token) {
        return TOKEN_DAO.getToken(token);
    }

    @Override
    public boolean saveToken(TokenDTO token) {
        return TOKEN_DAO.saveToken(token);
    }

    @Override
    public TokenDTO createToken(UserDTO userDTO) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUser(userDTO.getUser());
        tokenDTO.setToken(generateToken());
        TOKEN_DAO.saveToken(tokenDTO);
        return tokenDTO;
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace("-", "").toUpperCase();
        token = token.substring(0, 4) + "-" + token.substring(4, 8) + "-" + token.substring(8, 12);
        return token;
    }
}
