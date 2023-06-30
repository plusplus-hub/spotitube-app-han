package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.persistence.mapper.IUserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    @Mock
    private IUserMapper mapper;

    @Mock
    private IConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    void getUserByUsernameTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        UserDTO user = new UserDTO();
        user.setId(1);
        user.setUser("Test User");
        when(mapper.allColumnsToDTO(any(ResultSet.class))).thenReturn(user);

        UserDTO result = userDAO.getUserByUsername("Test User");

        assertNotNull(result);
        assertEquals("Test User", result.getUser());
    }

    @Test
    void getUserIdByTokenTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        UserDTO user = new UserDTO();
        user.setId(1);
        user.setUser("Test User");
        when(mapper.idToDTO(any(ResultSet.class))).thenReturn(user);

        UserDTO result = userDAO.getUserIdByToken("Test Token");

        assertNotNull(result);
        assertEquals(1, result.getId());
    }
}
