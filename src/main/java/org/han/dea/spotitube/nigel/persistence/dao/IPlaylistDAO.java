package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.model.PlaylistDTO;

public interface IPlaylistDAO {
    PlaylistDTO[] getAllPlaylistsByUserId(int userId);

    int getSumOfUsersPlaylistDuration(int userId);

    PlaylistDTO getPlaylistById(int playlistId, int userId);

    boolean savePlaylist(PlaylistDTO playlistDTO, int userId);

    boolean deletePlaylist(int playlistId, int userId);

    boolean isPlaylistOwner(int playlistId, int userId);

    boolean updatePlaylist(PlaylistDTO playlist, int playlistId, int id);
}
