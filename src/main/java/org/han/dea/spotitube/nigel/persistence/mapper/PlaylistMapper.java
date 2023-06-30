package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper implements IPlaylistMapper {

    @Override
    public PlaylistDTO allColumnsToDTO(ResultSet rs, int currentUserId) throws SQLException {
        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setId(rs.getInt("id"));
        playlist.setName(rs.getString("name"));

        // Compare the user_id associated with the playlist with the currentUserId
        int playlistUserId = rs.getInt("creator_id");
        playlist.setOwner(playlistUserId == currentUserId);

        return playlist;
    }

}
