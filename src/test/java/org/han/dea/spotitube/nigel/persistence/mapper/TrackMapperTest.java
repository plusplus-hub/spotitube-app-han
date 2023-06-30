package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.TrackDTO;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackMapperTest {

    private IMapper<TrackDTO> trackMapper;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        trackMapper = new TrackMapper();

        resultSet = mock(ResultSet.class);
    }

    @Test
    void testAllColumnsToDto_ValidResultSet_CreatesDto() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Test Track");
        when(resultSet.getString("performer")).thenReturn("Test Performer");
        when(resultSet.getInt("duration")).thenReturn(300);
        when(resultSet.getString("album")).thenReturn("Test Album");
        when(resultSet.getInt("playcount")).thenReturn(10);
        when(resultSet.getDate("publicationDate")).thenReturn(Date.valueOf("2023-01-01"));
        when(resultSet.getString("description")).thenReturn("Test Description");
        when(resultSet.getBoolean("offlineAvailable")).thenReturn(true);

        TrackDTO result = trackMapper.allColumnsToDTO(resultSet);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Track", result.getTitle());
        assertEquals("Test Performer", result.getPerformer());
        assertEquals(300, result.getDuration());
        assertEquals("Test Album", result.getAlbum());
        assertEquals(10, result.getPlaycount());
        assertEquals(Date.valueOf("2023-01-01"), result.getPublicationDate());
        assertEquals("Test Description", result.getDescription());
        assertTrue(result.isOfflineAvailable());
    }
}
