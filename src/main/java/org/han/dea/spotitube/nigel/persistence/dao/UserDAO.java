package org.han.dea.spotitube.nigel.persistence.dao;

import jakarta.inject.Inject;
import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.persistence.mapper.IUserMapper;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements IUserDAO {

    private final IUserMapper mapper;
    private final Logger logger;
    private final IConnectionFactory connectionFactory;

    @Inject
    public UserDAO(IUserMapper mapper, IConnectionFactory connectionFactory) {
        this.mapper = mapper;
        this.connectionFactory = connectionFactory;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        final String GET_USER_QUERY = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_USER_QUERY)) {
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return mapper.allColumnsToDTO(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting user by username from the database.", e);
            throw new DatabaseFailureException();
        }
    }

    @Override
    public UserDTO getUserIdByToken(String token) {
        final String GET_USER_FROM_TOKEN = "SELECT u.id FROM LoginTokens t inner join Users u on t.user_id = u.id WHERE token = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_USER_FROM_TOKEN)) {
            stm.setString(1, token);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return mapper.idToDTO(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Something went wrong while getting the user by token from the database.", e);
            throw new DatabaseFailureException();
        }
        return null;
    }
}