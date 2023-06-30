package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.model.PlaylistCollectionDTO;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.persistence.dao.IPlaylistDAO;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    private IPlaylistDAO playlistDAO;

    private PlaylistService playlistService;
    private IAuthService authService;
    private UserDTO testUser;
    private PlaylistDTO testPlaylist;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService(playlistDAO, authService);

        testUser = new UserDTO();
        testUser.setUser("testUser");
        testUser.setPassword("testPassword");
        testUser.setId(1);

        testPlaylist = new PlaylistDTO();
        testPlaylist.setId(1);
        testPlaylist.setName("testPlaylist");
    }

    @Test
    void testGetAllPlaylists_ValidUser_ReturnsPlaylistCollection() {
        PlaylistDTO[] playlists = new PlaylistDTO[]{testPlaylist};
        when(playlistDAO.getAllPlaylistsByUserId(testUser.getId())).thenReturn(playlists);
        when(playlistDAO.getSumOfUsersPlaylistDuration(testUser.getId())).thenReturn(100);

        PlaylistCollectionDTO result = playlistService.getAllPlaylists(testUser);
        assertNotNull(result);
        assertEquals(playlists, result.getPlaylists());
        assertEquals(100, result.getLength());
    }

    @Test
    void testCreatePlaylist_ValidData_ReturnsPlaylistCollection() {
        when(playlistDAO.savePlaylist(any(), anyInt())).thenReturn(true);
        assertDoesNotThrow(() -> playlistService.createPlaylist(testUser, testPlaylist));
    }

    @Test
    void testCreatePlaylist_InvalidData_ThrowsDatabaseFailureException() {
        when(playlistDAO.savePlaylist(any(), anyInt())).thenReturn(false);
        assertThrows(DatabaseFailureException.class, () -> playlistService.createPlaylist(testUser, testPlaylist));
    }

    @Test
    void testDeletePlaylist_ValidData_ReturnsPlaylistCollection() {
        when(playlistDAO.isPlaylistOwner(anyInt(), anyInt())).thenReturn(true);
        when(playlistDAO.deletePlaylist(anyInt(), anyInt())).thenReturn(true);
        assertDoesNotThrow(() -> playlistService.deletePlaylist(testUser, 1));
    }

    @Test
    void testDeletePlaylist_InvalidData_ThrowsDatabaseFailureException() {
        when(playlistDAO.isPlaylistOwner(anyInt(), anyInt())).thenReturn(false);
        assertThrows(DatabaseFailureException.class, () -> playlistService.deletePlaylist(testUser, 1));
    }

    @Test
    void testUpdatePlaylist_ValidData_ReturnsPlaylistCollection() {
        when(playlistDAO.isPlaylistOwner(anyInt(), anyInt())).thenReturn(true);
        when(playlistDAO.updatePlaylist(any(), anyInt(), anyInt())).thenReturn(true);
        assertDoesNotThrow(() -> playlistService.updatePlaylist(testUser, 1, testPlaylist));
    }

    @Test
    void testUpdatePlaylist_InvalidData_ThrowsDatabaseFailureException() {
        when(playlistDAO.isPlaylistOwner(anyInt(), anyInt())).thenReturn(false);
        assertThrows(DatabaseFailureException.class, () -> playlistService.updatePlaylist(testUser, 1, testPlaylist));
    }
}
