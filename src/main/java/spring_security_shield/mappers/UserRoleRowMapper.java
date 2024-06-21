package spring_security_shield.mappers;

import org.springframework.jdbc.core.RowMapper;
import spring_security_shield.dto.UserRoleDTO;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleRowMapper implements RowMapper<UserRoleDTO> {
    @Override
    public UserRoleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setIdUser(rs.getString("id_user"));
        userRoleDTO.setUsername(rs.getString("user_name"));
        userRoleDTO.setLastName(rs.getString("last_name"));
        userRoleDTO.setPassword(rs.getString("password"));
        userRoleDTO.setEmail(rs.getString("email"));
        userRoleDTO.setActive(rs.getBoolean("active"));
        userRoleDTO.setCreationDate(rs.getTimestamp("creation_date"));
        userRoleDTO.setModificationDate(rs.getTimestamp("modification_date"));
        userRoleDTO.setIdRoles(rs.getString("id_roles"));
        userRoleDTO.setNameRole(rs.getString("name_role"));
        return userRoleDTO;
    }
}