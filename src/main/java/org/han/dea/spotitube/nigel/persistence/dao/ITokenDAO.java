package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.model.TokenDTO;

public interface ITokenDAO {
    TokenDTO getToken(TokenDTO token);
    boolean saveToken(TokenDTO token);
}
