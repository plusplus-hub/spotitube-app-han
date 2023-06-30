package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.UserDTO;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

    private IUserMapper userMapper;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();

        resultSet = mock(ResultSet.class);
    }

    @Test
    void testAllColumnsToDto_ValidResultSet_CreatesDto() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn("Test User");
        when(resultSet.getString("password_hash")).thenReturn("hashedpassword");

        UserDTO result = userMapper.allColumnsToDTO(resultSet);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test User", result.getUser());
        assertEquals("hashedpassword", result.getPassword());
    }

    @Test
    void testIdToDto_ValidResultSet_CreatesDtoWithId() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);

        UserDTO result = userMapper.idToDTO(resultSet);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }
}
