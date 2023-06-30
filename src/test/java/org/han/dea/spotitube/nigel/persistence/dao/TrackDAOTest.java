package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.TrackDTO;
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

class TrackDAOTest {

    @Mock
    private IMapper<TrackDTO> mapper;

    @Mock
    private IConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TrackDAO trackDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    void getAllTracksFromPlaylistIdTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        TrackDTO track = new TrackDTO();
        track.setId(1);
        track.setTitle("Test Track");
        when(mapper.allColumnsToDTO(any(ResultSet.class))).thenReturn(track);

        TrackDTO[] result = trackDAO.getAllTracksFromPlaylistId(1);

        assertEquals(1, result.length);
        assertEquals("Test Track", result[0].getTitle());
    }

    @Test
    void addTrackToPlaylistTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        TrackDTO track = new TrackDTO();
        track.setId(1);
        track.setTitle("Test Track");

        boolean result = trackDAO.addTrackToPlaylist(1, track, 1);

        assertTrue(result);
    }

    @Test
    void deleteTrackFromPlaylistTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = trackDAO.deleteTrackFromPlaylist(1, 1, 1);

        assertTrue(result);
    }

    @Test
    void getAllTracksNotInPlaylistTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        TrackDTO track = new TrackDTO();
        track.setId(1);
        track.setTitle("Test Track");
        when(mapper.allColumnsToDTO(any(ResultSet.class))).thenReturn(track);

        TrackDTO[] result = trackDAO.getAllTracksNotInPlaylist(1);

        assertEquals(1, result.length);
        assertEquals("Test Track", result[0].getTitle());
    }
}
