package org.han.dea.spotitube.nigel.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.persistence.dao.IPlaylistDAO;
import org.han.dea.spotitube.nigel.model.PlaylistCollectionDTO;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;


public class PlaylistService implements IPlaylistService {

    private final IPlaylistDAO DAO;

    @Inject
    public PlaylistService(@Named("playlistDAO") IPlaylistDAO playlistDAO, IAuthService authService) {
        this.DAO = playlistDAO;
    }

    @Override
    public PlaylistCollectionDTO getAllPlaylists(UserDTO user) {
        PlaylistCollectionDTO encapsulatedPlaylists = new PlaylistCollectionDTO();
        PlaylistDTO[] playlists = DAO.getAllPlaylistsByUserId(user.getId());
        int length = DAO.getSumOfUsersPlaylistDuration(user.getId());
        encapsulatedPlaylists.setPlaylists(playlists);
        encapsulatedPlaylists.setLength(length);
        return encapsulatedPlaylists;
    }

    @Override
    public PlaylistCollectionDTO createPlaylist(UserDTO user, PlaylistDTO playlist) {
        boolean isSaved = DAO.savePlaylist(playlist, user.getId());
        if (!isSaved) {
            throw new DatabaseFailureException();
        } else {
            return getAllPlaylists(user);
        }
    }

    @Override
    public PlaylistCollectionDTO deletePlaylist(UserDTO user, int playlistId) {
        if (!isOwner(user, playlistId)) {
            throw new DatabaseFailureException();
        }

        boolean isDeleted = DAO.deletePlaylist(playlistId, user.getId());
        if (!isDeleted) {
            throw new DatabaseFailureException();
        } else {
            return getAllPlaylists(user);
        }
    }

    @Override
    public PlaylistCollectionDTO updatePlaylist(UserDTO user, int playlistId, PlaylistDTO playlist) {
        if (!isOwner(user, playlistId)) {
            throw new DatabaseFailureException();
        }
        boolean isUpdated = DAO.updatePlaylist(playlist, playlistId, user.getId());
        if (!isUpdated) {
            throw new DatabaseFailureException();
        } else {
            return getAllPlaylists(user);
        }
    }

    private boolean isOwner(UserDTO user, int playlistId) {
        return DAO.isPlaylistOwner(playlistId, user.getId());
    }

}
