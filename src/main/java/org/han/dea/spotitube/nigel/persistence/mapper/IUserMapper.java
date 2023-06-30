package org.han.dea.spotitube.nigel.persistence.mapper;

import org.han.dea.spotitube.nigel.model.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUserMapper {
    UserDTO allColumnsToDTO(ResultSet rs) throws SQLException;

    UserDTO idToDTO(ResultSet rs) throws SQLException;
}
