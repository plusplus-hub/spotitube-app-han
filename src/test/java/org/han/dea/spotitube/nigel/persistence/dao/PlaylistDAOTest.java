package org.han.dea.spotitube.nigel.persistence.dao;

import jakarta.inject.Inject;
import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.persistence.mapper.IPlaylistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
class PlaylistDAOTest {

    @Mock
    private IPlaylistMapper mapper;

    @Mock
    private IConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private PlaylistDAO playlistDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connectionFactory.getConnection()).thenReturn(connection);
    }

    @Test
    void getAllPlaylistsByUserIdTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setId(1);
        playlist.setName("Test Playlist");
        when(mapper.allColumnsToDTO(any(ResultSet.class), anyInt())).thenReturn(playlist);

        PlaylistDTO[] playlists = playlistDAO.getAllPlaylistsByUserId(1);

        assertThat(playlists).isNotEmpty();
        assertThat(playlists[0].getName()).isEqualTo("Test Playlist");
        assertThat(playlists[0].getId()).isEqualTo(1);
    }

    @Test
    void getSumOfUsersPlaylistDurationTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(100);

        int duration = playlistDAO.getSumOfUsersPlaylistDuration(1);

        assertEquals(100, duration);
    }

    @Test
    void getPlaylistByIdTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setId(1);
        playlist.setName("Test Playlist");
        when(mapper.allColumnsToDTO(any(ResultSet.class), anyInt())).thenReturn(playlist);

        PlaylistDTO result = playlistDAO.getPlaylistById(1, 1);

        assertEquals(1, result.getId());
        assertEquals("Test Playlist", result.getName());
    }

    @Test
    void savePlaylistTest() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setName("Test Playlist");

        boolean result = playlistDAO.savePlaylist(playlist, 1);

        assertTrue(result);
    }
}