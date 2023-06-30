package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.model.UserDTO;

public interface IUserDAO {
    UserDTO getUserByUsername(String username);

    UserDTO getUserIdByToken(String token);
}
