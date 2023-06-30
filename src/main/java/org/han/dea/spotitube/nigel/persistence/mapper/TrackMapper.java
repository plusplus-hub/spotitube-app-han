package org.han.dea.spotitube.nigel.persistence.mapper;

import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.model.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Named("TrackMapper")
public class TrackMapper implements IMapper<TrackDTO> {

        @Override
        public TrackDTO allColumnsToDTO(ResultSet resultSet) throws SQLException {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.setId(resultSet.getInt("id"));
            trackDTO.setTitle(resultSet.getString("title"));
            trackDTO.setPerformer(resultSet.getString("performer"));
            trackDTO.setDuration(resultSet.getInt("duration"));
            trackDTO.setAlbum(resultSet.getString("album"));
            trackDTO.setPlaycount(resultSet.getInt("playcount"));
            trackDTO.setPublicationDate(resultSet.getDate("publicationDate"));
            trackDTO.setDescription(resultSet.getString("description"));
            trackDTO.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));
            return trackDTO;
        }
}
