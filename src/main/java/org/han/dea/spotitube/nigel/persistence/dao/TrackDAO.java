package org.han.dea.spotitube.nigel.persistence.dao;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.TrackDTO;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.persistence.mapper.IMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackDAO implements ITrackDAO {

    private final Logger logger;
    private final IMapper<TrackDTO> mapper;
    private final IConnectionFactory connectionFactory;

    @Inject
    public TrackDAO(@Named("TrackMapper") IMapper<TrackDTO> mapper, IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.mapper = mapper;
        this.logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public TrackDTO[] getAllTracksFromPlaylistId(int playlistId) {
        final String GET_ALL_TRACKS_FROM_PLAYLIST = "SELECT * FROM Tracks INNER JOIN Playlist_Tracks PT on Tracks.id = PT.track_id WHERE PT.playlist_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_ALL_TRACKS_FROM_PLAYLIST)) {
            stm.setInt(1, playlistId);
            ResultSet rs = stm.executeQuery();
            List<TrackDTO> tracks = new ArrayList<>();
            while (rs.next()) {
                tracks.add(mapper.allColumnsToDTO(rs));
            }
            return tracks.toArray(new TrackDTO[0]);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Something went wrong while getting all tracks from playlist id from the database.", e);
            throw new DatabaseFailureException();
        }
    }

    @Override
    public boolean addTrackToPlaylist(int playlistId, TrackDTO track, int userId) {
        final String ADD_TRACK_TO_PLAYLIST = "INSERT INTO Playlist_Tracks (playlist_id, track_id) VALUES (?, ?)";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(ADD_TRACK_TO_PLAYLIST)) {
            stm.setInt(1, playlistId);
            stm.setInt(2, track.getId());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Something went wrong while adding track to playlist in the database.", e);
            throw new DatabaseFailureException();
        }
        return false;
    }

    @Override
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId, int userId) {
        final String DELETE_TRACK_FROM_PLAYLIST = "DELETE FROM Playlist_Tracks WHERE playlist_id = ? AND track_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_TRACK_FROM_PLAYLIST)) {
            stm.setInt(1, playlistId);
            stm.setInt(2, trackId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Something went wrong while deleting track from playlist in the database.", e);
            throw new DatabaseFailureException();
        }
        return false;
    }

    @Override
    public TrackDTO[] getAllTracksNotInPlaylist(int playlistId) {
        final String GET_ALL_TRACKS_NOT_IN_PLAYLIST =
                "SELECT * FROM Tracks " +
                        "WHERE id NOT IN (" +
                        "SELECT track_id FROM Playlist_Tracks WHERE playlist_id = ?" +
                        ")";

        List<TrackDTO> tracks = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_ALL_TRACKS_NOT_IN_PLAYLIST)) {
            stm.setInt(1, playlistId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tracks.add(mapper.allColumnsToDTO(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Something went wrong while getting tracks not in playlist from the database.", e);
            throw new DatabaseFailureException();
        }
        return tracks.toArray(new TrackDTO[0]);
    }

}
