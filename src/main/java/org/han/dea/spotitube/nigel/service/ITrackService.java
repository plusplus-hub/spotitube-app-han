package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.model.TrackDTO;

public interface ITrackService {

    PlaylistDTO getAllTracksFromPlaylist(int playlistId, int userId);

    PlaylistDTO addTrackToPlaylist(int playlistId, TrackDTO trackId, int userId);

    PlaylistDTO deleteTrackFromPlaylist(int playlistId, int trackId, int id);

    PlaylistDTO getAllTracksNotInPlaylist(int playlistId, int userId);
}
