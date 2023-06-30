package org.han.dea.spotitube.nigel.persistence.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapper<T> {
    T allColumnsToDTO(ResultSet rs) throws SQLException;
}
