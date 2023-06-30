package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.exception.PlaylistNotFoundException;
import org.han.dea.spotitube.nigel.exception.TrackNotAddedException;
import org.han.dea.spotitube.nigel.exception.TrackNotDeletedException;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.TrackDTO;
import org.han.dea.spotitube.nigel.persistence.dao.IPlaylistDAO;
import org.han.dea.spotitube.nigel.persistence.dao.ITrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackServiceTest {

    @InjectMocks
    private TrackService trackService;

    @Mock
    private ITrackDAO trackDao;

    @Mock
    private IPlaylistDAO playDao;

    private final int playlistId = 1;
    private final int userId = 2;
    private final TrackDTO track = new TrackDTO();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTracksFromPlaylist() {
        //Assert
        PlaylistDTO playlist = new PlaylistDTO();
        when(playDao.getPlaylistById(playlistId, userId)).thenReturn(playlist);
        when(trackDao.getAllTracksFromPlaylistId(playlistId)).thenReturn(new TrackDTO[]{track});

        // When
        PlaylistDTO result = trackService.getAllTracksFromPlaylist(playlistId, userId);

        // Then
        assertNotNull(result);
        assertEquals(playlist, result);
    }

    @Test
    void testGetAllTracksFromPlaylistWithPlaylistNotFoundException() {
        //Assert
        when(playDao.getPlaylistById(playlistId, userId)).thenReturn(null);

        // Then
        assertThrows(PlaylistNotFoundException.class, () -> trackService.getAllTracksFromPlaylist(playlistId, userId));
    }

    @Test
    void testAddTrackToPlaylist() {
        // Arrange
        when(playDao.getPlaylistById(playlistId, userId)).thenReturn(new PlaylistDTO());
        when(trackDao.addTrackToPlaylist(playlistId, track, userId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> trackService.addTrackToPlaylist(playlistId, track, userId));
    }

    @Test
    void testAddTrackToPlaylistWithTrackNotAddedException() {
        // Arrange
        when(trackDao.addTrackToPlaylist(playlistId, track, userId)).thenReturn(false);

        // Act & Assert
        assertThrows(TrackNotAddedException.class, () -> trackService.addTrackToPlaylist(playlistId, track, userId));
    }

    @Test
    void testDeleteTrackFromPlaylist() {
        // Arrange
        when(trackDao.deleteTrackFromPlaylist(playlistId, track.getId(), userId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> trackService.deleteTrackFromPlaylist(playlistId, track.getId(), userId));
    }

    @Test
    void testDeleteTrackFromPlaylistWithTrackNotDeletedException() {
        // Arrange
        when(trackDao.deleteTrackFromPlaylist(playlistId, track.getId(), userId)).thenReturn(false);

        // Act & Assert
        assertThrows(TrackNotDeletedException.class, () -> trackService.deleteTrackFromPlaylist(playlistId, track.getId(), userId));
    }

}
