package org.han.dea.spotitube.nigel.persistence.dao;

import jakarta.inject.Inject;
import org.han.dea.spotitube.nigel.config.IConnectionFactory;
import org.han.dea.spotitube.nigel.model.PlaylistDTO;
import org.han.dea.spotitube.nigel.exception.DatabaseFailureException;
import org.han.dea.spotitube.nigel.persistence.mapper.IPlaylistMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAO implements IPlaylistDAO {

    private final IPlaylistMapper MAPPER;
    private final Logger LOGGER;
    private final IConnectionFactory connectionFactory;

    @Inject
    public PlaylistDAO(IPlaylistMapper mapper, IConnectionFactory connectionFactory) {
        MAPPER = mapper;
        this.connectionFactory = connectionFactory;
        LOGGER = Logger.getLogger(getClass().getName());
    }

    @Override
    public PlaylistDTO[] getAllPlaylistsByUserId(int userId) {
        final String GET_ALL_PLAYLISTS_BY_USER_ID = "SELECT * FROM Playlists INNER JOIN User_Playlists UP on Playlists.id = UP.playlist_id WHERE UP.user_id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_ALL_PLAYLISTS_BY_USER_ID)) {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            List<PlaylistDTO> playlists = new ArrayList<>();
            while (rs.next()) {
                playlists.add(MAPPER.allColumnsToDTO(rs, userId));
            }
            return playlists.toArray(new PlaylistDTO[0]);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting all playlists by user id from the database.", e);
            throw new DatabaseFailureException();
        }
    }

    @Override
    public int getSumOfUsersPlaylistDuration(int userId) {
        final String GET_SUM_OF_USERS_PLAYLIST_DURATION =
                "SELECT SUM(Tracks.duration) as totalDuration " +
                "FROM Users " +
                "JOIN User_Playlists ON Users.id = User_Playlists.user_id " +
                "JOIN Playlist_Tracks ON User_Playlists.playlist_id = Playlist_Tracks.playlist_id " +
                "JOIN Tracks ON Playlist_Tracks.track_id = Tracks.id " +
                "WHERE Users.id = ? " +
                "GROUP BY Users.id;";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_SUM_OF_USERS_PLAYLIST_DURATION)) {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting sum of users playlist duration from the database.", e);
            throw new DatabaseFailureException();
        }
        return 0;
    }

    @Override
    public PlaylistDTO getPlaylistById(int playlistId, int userId) {
        final String GET_PLAYLIST_BY_ID = "SELECT * FROM Playlists INNER JOIN User_Playlists UP on Playlists.id = UP.playlist_id WHERE UP.user_id = ? AND UP.playlist_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_PLAYLIST_BY_ID)) {
            stm.setInt(1, userId);
            stm.setInt(2, playlistId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return MAPPER.allColumnsToDTO(rs, 0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting playlist by id from the database.", e);
            throw new DatabaseFailureException();
        }
        return null;
    }

    @Override
    public boolean savePlaylist(PlaylistDTO playlistDTO, int userId) {
        final String SAVE_PLAYLIST = "INSERT INTO Playlists (name, creator_id) VALUES (?, ?)";
        final String SAVE_USER_PLAYLIST = "INSERT INTO User_Playlists (user_id, playlist_id) VALUES (?, ?)";

        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement stm = conn.prepareStatement(SAVE_PLAYLIST, Statement.RETURN_GENERATED_KEYS)) {
                stm.setString(1, playlistDTO.getName());
                stm.setInt(2, userId);
                stm.executeUpdate();
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int playlistId = rs.getInt(1);
                    try (PreparedStatement stm2 = conn.prepareStatement(SAVE_USER_PLAYLIST)) {
                        stm2.setInt(1, userId);
                        stm2.setInt(2, playlistId);
                        stm2.executeUpdate();
                        conn.commit();  // End transaction successfully
                        return true;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction in case of an error
                LOGGER.log(Level.SEVERE, "Something went wrong while saving playlist to the database.", e);
                throw new DatabaseFailureException();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting connection or during the transaction.", e);
            throw new DatabaseFailureException();
        }
        return false;
    }

    @Override
    public boolean deletePlaylist(int playlistId, int userId) {
        final String DELETE_USER_PLAYLIST = "DELETE FROM User_Playlists WHERE playlist_id = ? AND user_id = ?";
        final String DELETE_PLAYLIST_TRACKS = "DELETE FROM Playlist_Tracks WHERE playlist_id = ?";
        final String DELETE_PLAYLIST = "DELETE FROM Playlists WHERE id = ? AND creator_id = ?";

        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement stm = conn.prepareStatement(DELETE_USER_PLAYLIST)) {
                stm.setInt(1, playlistId);
                stm.setInt(2, userId);
                stm.executeUpdate();

                try (PreparedStatement stm2 = conn.prepareStatement(DELETE_PLAYLIST_TRACKS)) {
                    stm2.setInt(1, playlistId);
                    stm2.executeUpdate();

                    try (PreparedStatement stm3 = conn.prepareStatement(DELETE_PLAYLIST)) {
                        stm3.setInt(1, playlistId);
                        stm3.setInt(2, userId);
                        int rowsAffected = stm3.executeUpdate();
                        if (rowsAffected > 0) {
                            conn.commit();  // End transaction successfully
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction in case of an error
                LOGGER.log(Level.SEVERE, "Something went wrong while deleting playlist from the database while deleting playlist from the database.", e);
                throw new DatabaseFailureException();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while getting connection or during the transaction while deleting playlist from the database.", e);
            throw new DatabaseFailureException();
        }
        return false;
    }

    @Override
    public boolean isPlaylistOwner(int playlistId, int userId) {
        final String IS_PLAYLIST_OWNER = "SELECT * FROM Playlists WHERE id = ? AND creator_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(IS_PLAYLIST_OWNER)) {
            stm.setInt(1, playlistId);
            stm.setInt(2, userId);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while checking if user is playlist owner in the database.", e);
            throw new DatabaseFailureException();
        }
    }

    @Override
    public boolean updatePlaylist(PlaylistDTO playlist, int playlistId, int id) {
        final String UPDATE_PLAYLIST = "UPDATE Playlists SET name = ? WHERE id = ? AND creator_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_PLAYLIST)) {
            stm.setString(1, playlist.getName());
            stm.setInt(2, playlistId);
            stm.setInt(3, id);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong while updating playlist in the database.", e);
            throw new DatabaseFailureException();
        }
    }
}