package org.han.dea.spotitube.nigel.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.persistence.dao.IUserDAO;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.exception.UnauthorizedUserException;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService implements IAuthService {

    private final IUserDAO USER_DAO;

    @Inject
    public AuthService(@Named("userDAO") IUserDAO userDAO) {
        this.USER_DAO = userDAO;
    }

    @Override
    public boolean validatePassword(UserDTO user) {
        UserDTO userInDB = getUserByUsername(user.getUser());
        if (userInDB == null) {
            throw new UnauthorizedUserException();
        }
        return checkPassword(user.getPassword(), userInDB.getPassword());
    }

    @Override
    public UserDTO getUserByToken(String token) {
        UserDTO user = USER_DAO.getUserIdByToken(token);
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return user;
    }

    private boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    private UserDTO getUserByUsername(String username) {
        return USER_DAO.getUserByUsername(username);
    }
}