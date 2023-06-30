package org.han.dea.spotitube.nigel.persistence.mapper;

import jakarta.inject.Named;
import org.han.dea.spotitube.nigel.model.TokenDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

@Named("tokenMapper")
public class TokenMapper implements IMapper<TokenDTO> {

    @Override
    public TokenDTO allColumnsToDTO(ResultSet rs) throws SQLException {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(rs.getString("token"));
        tokenDTO.setUser(rs.getString("username"));
        return tokenDTO;
    }
}
