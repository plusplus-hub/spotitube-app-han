package org.han.dea.spotitube.nigel.persistence.dao;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.persistence.mapper.IMapper;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class TokenDAO implements ITokenDAO {

    private final IConnectionFactory connectionFactory;
    private final IMapper<TokenDTO> MAPPER;
    private final Logger LOGGER;

    @Inject
    public TokenDAO(@Named("tokenMapper") IMapper<TokenDTO> mapper, IConnectionFactory connectionFactory) {
        this.MAPPER = mapper;
        this.LOGGER = Logger.getLogger(getClass().getName());
        this.connectionFactory = connectionFactory;
    }

    @Override
    public TokenDTO getToken(TokenDTO token) {
        TokenDTO result = null;
        final String GET_TOKEN = "SELECT token, u.full_name FROM LoginTokens t inner join Users u on t.user_id = u.id WHERE username = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_TOKEN)) {
            stm.setString(1, token.getUser());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                result = MAPPER.allColumnsToDTO(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting the token from the database.", e);
            throw new DatabaseFailureException();
        }
        return result;
    }

    @Override
    public boolean saveToken(TokenDTO token) {
        final String INSERT_TOKEN = "INSERT INTO LoginTokens (token, user_id) SELECT ?, id FROM Users WHERE username = ?";
        return executeDml(token, INSERT_TOKEN);
    }

    private boolean executeDml(TokenDTO tokenDTO, String sql) {
        boolean result = false;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tokenDTO.getToken());
            stmt.setString(2, tokenDTO.getUser());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during inserting, deleting or updating token", e);
            throw new DatabaseFailureException();
        }
        return result;
    }
}
