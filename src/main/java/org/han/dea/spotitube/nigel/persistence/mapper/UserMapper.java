package org.han.dea.spotitube.nigel.persistence.mapper;

import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.model.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Named("userMapper")
public class UserMapper implements IUserMapper {

    @Override
    public UserDTO allColumnsToDTO(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(rs.getInt("id"));
        user.setUser(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        return user;
    }

    @Override
    public UserDTO idToDTO(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(rs.getInt("id"));
        return user;
    }
}
