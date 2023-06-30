package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.han.dea.spotitube.nigel.persistence.mapper.IMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TokenDAOTest {

    @Mock
    private IMapper<TokenDTO> mapper;

    @Mock
    private IConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TokenDAO tokenDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    void getTokenTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        TokenDTO token = new TokenDTO();
        token.setToken("Test Token");
        token.setUser("Test User");
        when(mapper.allColumnsToDTO(any(ResultSet.class))).thenReturn(token);

        TokenDTO result = tokenDAO.getToken(token);

        assertEquals("Test Token", result.getToken());
        assertEquals("Test User", result.getUser());
    }

    @Test
    void saveTokenTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        TokenDTO token = new TokenDTO();
        token.setToken("Test Token");
        token.setUser("Test User");

        boolean result = tokenDAO.saveToken(token);

        assertTrue(result);
    }

}
