package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistMapperTest {

    private IPlaylistMapper playlistMapper;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        playlistMapper = new PlaylistMapper();

        resultSet = mock(ResultSet.class);
    }

    @Test
    void testAllColumnsToDto_ValidResultSet_CreatesDto() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Test Playlist");
        when(resultSet.getInt("creator_id")).thenReturn(2);

        PlaylistDTO result = playlistMapper.allColumnsToDTO(resultSet, 2);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Playlist", result.getName());
        assertTrue(result.isOwner());
    }

    @Test
    void testAllColumnsToDto_ValidResultSet_NonOwner() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Test Playlist");
        when(resultSet.getInt("creator_id")).thenReturn(3);

        PlaylistDTO result = playlistMapper.allColumnsToDTO(resultSet, 2);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Playlist", result.getName());
        assertFalse(result.isOwner());
    }
}
