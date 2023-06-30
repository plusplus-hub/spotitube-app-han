package org.han.dea.spotitube.nigel.service;

import jakarta.inject.Inject;
import org.han.dea.spotitube.nigel.exception.PlaylistNotFoundException;
import org.han.dea.spotitube.nigel.exception.TrackNotAddedException;
import org.han.dea.spotitube.nigel.exception.TrackNotDeletedException;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.persistence.dao.IPlaylistDAO;
import org.han.dea.spotitube.nigel.persistence.dao.ITrackDAO;
import org.han.dea.spotitube.nigel.model.TrackDTO;

/**
 * The TrackService class provides methods for interacting with Tracks
 * and Playlists in the Spotitube application.
 */
public class TrackService implements ITrackService {

    private final ITrackDAO trackDao;
    private final IPlaylistDAO playDao;

    /**
     * Constructor for TrackService class that takes two parameters that require injection.
     *
     * @param trackDao interface object that provides methods for interacting with Tracks
     * @param playDao  interface object that provides methods for interacting with Playlists
     */
    @Inject
    public TrackService(ITrackDAO trackDao, IPlaylistDAO playDao) {
        this.trackDao = trackDao;
        this.playDao = playDao;
    }

    /**
     * Returns a PlaylistDTO object that contains all tracks from the specified playlist.
     *
     * @param playlistId the id of the playlist
     * @param userId     the id of the user
     * @return a PlaylistDTO that contains all tracks from the specified playlist.
     * @throws PlaylistNotFoundException if the specified playlist is not found.
     */
    @Override
    public PlaylistDTO getAllTracksFromPlaylist(int playlistId, int userId) {
        PlaylistDTO playlist = playDao.getPlaylistById(playlistId, userId);
        if (playlist == null) {
            throw new PlaylistNotFoundException();
        }

        TrackDTO[] tracks = trackDao.getAllTracksFromPlaylistId(playlistId);
        playlist.setTracks(tracks);
        return playlist;
    }

    /**
     * Adds a track to a specified playlist and returns the updated playlist.
     *
     * @param playlistId the id of the playlist
     * @param trackId    the track to be added to the playlist
     * @param userId     the id of the user
     * @return a PlaylistDTO that contains all tracks from the specified playlist including the newly added one.
     * @throws TrackNotAddedException if the track cannot be added to the playlist.
     */
    @Override
    public PlaylistDTO addTrackToPlaylist(int playlistId, TrackDTO trackId, int userId) {
        boolean isAdded = trackDao.addTrackToPlaylist(playlistId, trackId, userId);
        if (isAdded) {
            throw new TrackNotAddedException();
        }
        return getAllTracksFromPlaylist(playlistId, userId);
    }

    /**
     * Deletes a track from a specified playlist and returns the updated playlist.
     *
     * @param playlistId the id of the playlist
     * @param trackId    the id of the track to be deleted
     * @param userId     the id of the user
     * @return a PlaylistDTO that contains all remaining tracks from the specified playlist after deletion.
     * @throws TrackNotDeletedException if the track cannot be deleted from the playlist.
     */
    @Override
    public PlaylistDTO deleteTrackFromPlaylist(int playlistId, int trackId, int userId) {
        boolean isDeleted = trackDao.deleteTrackFromPlaylist(playlistId, trackId, userId);
        if (!isDeleted) {
            throw new TrackNotDeletedException();
        }
        return getAllTracksFromPlaylist(playlistId, userId);
    }

    /**
     * Returns a PlaylistDTO object that contains all tracks not present in the specified playlist.
     *
     * @param playlistId the id of the playlist
     * @param userId     the id of the user
     * @return a PlaylistDTO that contains all tracks not present in the specified playlist.
     * @throws PlaylistNotFoundException if the specified playlist is not found.
     */
    @Override
    public PlaylistDTO getAllTracksNotInPlaylist(int playlistId, int userId) {
        PlaylistDTO playlist = playDao.getPlaylistById(playlistId, userId);
        if (playlist == null) {
            throw new PlaylistNotFoundException();
        }
        TrackDTO[] tracks = trackDao.getAllTracksNotInPlaylist(playlistId);
        playlist.setTracks(tracks);
        return playlist;
    }

}
