package org.han.dea.spotitube.nigel.persistence.dao;

import org.han.dea.spotitube.nigel.model.TrackDTO;

public interface ITrackDAO {

    TrackDTO[] getAllTracksFromPlaylistId(int playlistId);

    boolean addTrackToPlaylist(int playlistId, TrackDTO trackId, int userId);

    boolean deleteTrackFromPlaylist(int playlistId, int trackId, int userId);

    TrackDTO[] getAllTracksNotInPlaylist(int playlistId);
}
