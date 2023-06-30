package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.model.PlaylistCollectionDTO;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;

public interface IPlaylistService {

    PlaylistCollectionDTO getAllPlaylists(UserDTO user);

    PlaylistCollectionDTO createPlaylist(UserDTO user, PlaylistDTO playlist);

    PlaylistCollectionDTO deletePlaylist(UserDTO user, int playlistId);

    PlaylistCollectionDTO updatePlaylist(UserDTO user, int playlistId, PlaylistDTO playlist);
}
