package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenMapperTest {

    private IMapper<TokenDTO> tokenMapper;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        tokenMapper = new TokenMapper();

        resultSet = mock(ResultSet.class);
    }

    @Test
    void testAllColumnsToDto_ValidResultSet_CreatesDto() throws SQLException {
        when(resultSet.getString("token")).thenReturn("ABCD-EFGH-IJKL");
        when(resultSet.getString("username")).thenReturn("Test User");

        TokenDTO result = tokenMapper.allColumnsToDTO(resultSet);

        assertNotNull(result);
        assertEquals("ABCD-EFGH-IJKL", result.getToken());
        assertEquals("Test User", result.getUser());
    }
}
