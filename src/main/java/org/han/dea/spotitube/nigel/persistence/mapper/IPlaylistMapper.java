package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IPlaylistMapper {
    PlaylistDTO allColumnsToDTO(ResultSet rs, int currentUserId) throws SQLException;
}
