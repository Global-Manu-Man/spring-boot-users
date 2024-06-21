package spring_security_shield.mappers;
import org.springframework.jdbc.core.RowMapper;
import spring_security_shield.entity.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserRowMapper implements RowMapper<Users>{
    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users user = new Users();
        user.setId(rs.getString("id_user"));
        user.setUsername(rs.getString("user_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        //user.setPassword(rs.getString("password"));
        user.setActivo(rs.getBoolean("active"));
        user.setFechaCreacion(rs.getTimestamp("creation_date") != null ? Date.from(rs.getTimestamp("creation_date").toInstant()) : null);
        user.setFechaModificacion(rs.getTimestamp("modification_date") != null ? Date.from(rs.getTimestamp("modification_date").toInstant()) : null);
        user.setNameRole(rs.getString("name_role"));
        return user;
    }
}
